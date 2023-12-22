package flyingshooter.android.data.repositories

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import android.util.Log
import flyingshooter.android.data.datasource.remote.game.events.recieved.ServerEventData
import flyingshooter.android.data.datasource.remote.game.events.sent.toData
import flyingshooter.android.data.util.SignatureUtil
import flyingshooter.android.domain.entities.GameId
import flyingshooter.android.domain.entities.GameInfo
import flyingshooter.android.domain.entities.GameType
import flyingshooter.android.domain.entities.game.events.recieved.ServerEvent
import flyingshooter.android.domain.entities.game.events.sent.ClientEvent
import flyingshooter.android.domain.entities.server.GameServerInfo
import flyingshooter.android.domain.entities.server.GameServerRepository
import flyingshooter.shared.domain.util.required
import flyingshooter.shared.presentation.routes.games.PostGameResponse
import flyingshooter.shared.presentation.routes.games.GetGamesResponse
import flyingshooter.shared.presentation.routes.sessions.ClientInfo
import flyingshooter.shared.presentation.routes.sessions.CreateSessionRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.receiveDeserialized
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive

class DefaultGameServerRepository(
    private val context: Context, private val serverInfo: GameServerInfo
) : GameServerRepository {

    private lateinit var sentGameEventsStream: DefaultClientWebSocketSession
    private val serverEventObserver = MutableSharedFlow<ServerEvent>()
    private fun loadAndroidAppInfo(): ClientInfo.AndroidApp {
        val packageInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return ClientInfo.AndroidApp(
            packageName = context.packageName,
            versionCode = packageInfo.versionCode.toLong(),
            sha256CertFingerprints = SignatureUtil.selfSHA256Signatures(context).required(),
            installerPackageName = context.packageManager.getInstallerPackageName(context.packageName),
            firstInstallTime = packageInfo.firstInstallTime,
        )
    }

    private val httpClient = HttpClient(CIO) {
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json()
        }
        install(Auth) {
            bearer {
                this.loadTokens {
                    authToken?.let {
                        BearerTokens(it, "REFRESH_TOKEN")
                    }
                }
                refreshTokens {
                    val response = client.post("sessions") {
                        contentType(ContentType.Application.Json)
                        setBody(
                            CreateSessionRequest(
                                client = ClientInfo(
                                    deviceName = "${Build.MANUFACTURER} ${Build.MODEL}",
                                    androidApp = loadAndroidAppInfo()
                                )
                            )
                        )
                    }
                    response.headers["Authorization"]?.split("Bearer ")?.last()?.let {
                        authToken = it
                        BearerTokens(it, "REFRESH_TOKEN")
                    }
                }
            }
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.e("HTTP Client", message)
                }
            }
            level = LogLevel.ALL
        }

        install(WebSockets) {
            pingInterval = 10_000
        }

        defaultRequest {
            url.host = serverInfo.host
            url.port = serverInfo.port
        }
        headers {
            "Authorization" to "Bearer YourAccessToken"
            "Content-Type" to "application/json"
        }
    }
    override var authToken: String? = null

    override suspend fun getGames(): List<GameInfo> {
        return httpClient.get("/games") {}.body<GetGamesResponse>().games.map {
            GameInfo(
                it.id, it.name
            )
        }
    }

    override suspend fun createGame(gameType: GameType): GameId {
        return httpClient.post("games") {}.body<PostGameResponse>().id
    }

    override suspend fun stopGame(gameId: GameId) {
        return httpClient.delete("games/$gameId") {}.body()
    }

    override suspend fun observeGameEvents(gameId: GameId): Flow<ServerEvent> {
        if (::sentGameEventsStream.isInitialized.not()) {
            httpClient.webSocket(
                method = HttpMethod.Get,
                host = serverInfo.host,
                port = serverInfo.port,
                path = "/games/$gameId"
            ) {
                sentGameEventsStream = this
                while (isActive) {
                    serverEventObserver.emit(receiveDeserialized<ServerEventData>().toDomainEntity())
                }
            }
        }
        return serverEventObserver
    }

    override suspend fun sendGameEvent(gameId: GameId, event: ClientEvent) {
        sentGameEventsStream.sendSerialized(event.toData())
    }
}