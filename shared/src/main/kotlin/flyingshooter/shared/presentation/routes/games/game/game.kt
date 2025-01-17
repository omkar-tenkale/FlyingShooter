package flyingshooter.shared.presentation.routes.games.game

import flyingshooter.shared.data.BaseServerEventData
import flyingshooter.shared.data.TestEvent
import flyingshooter.shared.domain.entities.connection.Client
import flyingshooter.shared.domain.entities.game.events.ObserveGameEventsUseCase
import flyingshooter.shared.domain.entities.game.events.OnClientEventReceivedUseCase
import flyingshooter.shared.domain.entities.game.events.ServerEvent
import flyingshooter.shared.domain.entities.game.events.ServerEventType
import flyingshooter.shared.domain.util.NanoId
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.route
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.server.websocket.receiveDeserialized
import io.ktor.server.websocket.sendSerialized
import io.ktor.server.websocket.webSocket
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.java.KoinJavaComponent.getKoin
import java.util.concurrent.ConcurrentHashMap

const val PARAM_GAME_ID = "gameId"
internal fun Route.gameRoute() {
    route("/{$PARAM_GAME_ID}"){

        webSocket{
            handleNewConnection()
        }
    }
}

private suspend fun DefaultWebSocketServerSession.handleNewConnection() {
    val connections: ConcurrentHashMap<Client, DefaultWebSocketServerSession> = ConcurrentHashMap()

    val gameId = call.parameters[PARAM_GAME_ID]!!
    val client = Client()
    connections += client to this

    getKoin().getScope(gameId).let { scope ->
        scope.get<ObserveGameEventsUseCase>()(client.id).onEach{
            sendSerialized(it.toData())
        }.launchIn(GlobalScope)

        val onConnectionEventUseCase = scope.get<OnClientEventReceivedUseCase>()
        for(frame in incoming) {
            onConnectionEventUseCase(client,receiveDeserialized())
        }
    }
}

private fun ServerEvent.toData() =  BaseServerEventData(
    NanoId.generate(),
    ServerEventType.MAP_CHANGED.toString(),
    TestEvent(text)
)