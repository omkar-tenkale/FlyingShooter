package flyingshooter.shared.presentation

import com.auth0.jwt.JWT
import flyingshooter.shared.data.dataModule
import flyingshooter.shared.data.repositories.algorithm
import flyingshooter.shared.domain.domainModule
import flyingshooter.shared.presentation.routes.games.gamesRoute
import flyingshooter.shared.presentation.routes.sessions.sessionsRoute
import flyingshooter.shared.presentation.routes.test.healthRoute
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import kotlinx.serialization.json.Json
import org.koin.ktor.plugin.Koin
import java.time.Duration

fun GameServer(port: Int = 5383): GameServer {
    return DefaultGameServer(port)
}

private class DefaultGameServer(port: Int) : GameServer {
    private val instance by lazy {
        embeddedServer(Netty, port = port, module = {
            install(ContentNegotiation){
                json()
            }
            install(Authentication) {
                jwt {
                    verifier(JWT.require(algorithm).build())
                    validate { credential ->
                        JWTPrincipal(credential.payload)
                    }
                    challenge { _, _ ->
                        call.respond(HttpStatusCode.Unauthorized)
                    }
                }
            }
            install(Koin) {
                modules(dataModule, domainModule, presentationModule)
            }
            install(StatusPages) {
                exception<Throwable> { call, cause ->
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        cause.localizedMessage ?: "Internal Server Error"
                    )
                }
            }
            install(WebSockets) {
                pingPeriod = Duration.ofSeconds(15)
                timeout = Duration.ofSeconds(15)
                maxFrameSize = Long.MAX_VALUE
                masking = false
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }
            routing {
                sessionsRoute()
                authenticate {
                    healthRoute()
                    gamesRoute()
                }
            }
        })
    }

    override fun start() {
        instance.start(wait = false)
    }

    override fun stop() {
        instance.stop()
    }
}