package flyingshooter.android.data.repositories

import flyingshooter.android.data.datasource.remote.game.events.recieved.ServerEventData
import flyingshooter.android.data.datasource.remote.game.events.sent.toData
import flyingshooter.android.data.repositories.serverevents.DefaultServerEventsDeserializationHandler
import flyingshooter.android.data.repositories.serverevents.ServerEventsDeserializationHandler
import flyingshooter.shared.domain.util.Resource
import flyingshooter.core.domain.game.GameId
import flyingshooter.core.domain.game.GameInfo
import flyingshooter.core.domain.game.events.ServerEvent
import flyingshooter.core.domain.game.events.sent.ClientEvent
import flyingshooter.core.domain.game.GameRepository
import flyingshooter.android.domain.entities.server.GameServerInfo
import flyingshooter.shared.domain.util.Deserializer
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readBytes
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive

private val logger = KotlinLogging.logger {}

class DefaultGameRepository(
    private val gameInfo: GameInfo,
    private val serverInfo: GameServerInfo,
    private val deserializer: Deserializer,
    private val gameServerRepository: DefaultGameServerRepository,
) : GameRepository {

    private lateinit var sentGameEventsStream: DefaultClientWebSocketSession
    private val serverEventObserver = MutableStateFlow<Resource<ServerEvent>>(Resource.Loading())
    private val handler: ServerEventsDeserializationHandler =
        DefaultServerEventsDeserializationHandler(this)

    override suspend fun observeGameEvents(gameId: GameId): Flow<Resource<ServerEvent>> {
        if (::sentGameEventsStream.isInitialized.not()) {
            gameServerRepository.httpClient.webSocket(
                method = HttpMethod.Get,
                host = serverInfo.host,
                port = serverInfo.port,
                path = "/games/$gameId"
            ) {
                try {
                    sentGameEventsStream = this
                    while (isActive) {
                        for (frame in incoming) {
                            when (frame) {
                                is Frame.Text -> {
                                    val data = frame.readText()
                                    val typeIdentifier =
                                        deserializer.decodeFromString<BaseServerEventType>(
                                            data,
                                            isLenient = true
                                        )
                                    handler.handle(typeIdentifier.type, data)
                                }

                                is Frame.Binary -> {
                                    val bytes = frame.readBytes()
                                }

                                else -> logger.warn { "Unsupported frame received in websocket" }

                            }
                        }
//                        serverEventObserver.emit(Resource.Success(receiveDeserialized<ServerEventData>().toDomainEntity()))
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    serverEventObserver.emit(Resource.Error(e))
                }
            }
        }
        return serverEventObserver
    }

    override suspend fun sendGameEvent(gameId: GameId, event: ClientEvent) {
        sentGameEventsStream.sendSerialized(event.toData())
    }
}