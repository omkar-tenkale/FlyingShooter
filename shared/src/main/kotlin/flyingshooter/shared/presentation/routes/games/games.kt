package flyingshooter.shared.presentation.routes.games

import flyingshooter.shared.domain.entities.game.CreateGameUseCase
import flyingshooter.shared.domain.entities.game.GetActiveGamesUseCase
import flyingshooter.shared.domain.entities.game.deathmatch.DeathMatchGame
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import org.koin.ktor.plugin.scope

internal fun Route.gamesRoute() {
    route("/games"){
        get {
            call.respond(ListGameResponse(games = call.scope.get<GetActiveGamesUseCase>()().map{ ListGameResponse.GameInfo(it.id,it.name) }))
        }
        post {
            DeathMatchGame().also {
                call.scope.get<CreateGameUseCase>()(it)
                call.respond(CreateGameResponse(it.id))
            }
        }
    }
}