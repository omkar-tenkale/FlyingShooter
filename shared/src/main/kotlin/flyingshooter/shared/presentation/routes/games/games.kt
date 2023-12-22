package flyingshooter.shared.presentation.routes.games

import flyingshooter.shared.domain.entities.game.CreateGameUseCase
import flyingshooter.shared.domain.entities.game.EndGameUseCase
import flyingshooter.shared.domain.entities.game.GetActiveGamesUseCase
import flyingshooter.shared.domain.entities.game.deathmatch.DeathMatchGame
import flyingshooter.shared.presentation.routes.games.game.PARAM_GAME_ID
import flyingshooter.shared.presentation.serverScope
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

internal fun Route.gamesRoute() {
    route("/games"){
        get {
            call.respond(GetGamesResponse(games = call.serverScope.get<GetActiveGamesUseCase>()().map{ GetGamesResponse.GameInfo(it.id,it.name) }))
        }
        post {
            DeathMatchGame().also {
                call.serverScope.get<CreateGameUseCase>()(it)
                call.respond(PostGameResponse(it.id))
            }
        }
        route("/{$PARAM_GAME_ID}"){
            delete {
                val gameId = call.parameters[PARAM_GAME_ID]!!
                call.serverScope.get<EndGameUseCase>()(gameId)
                call.respond(HttpStatusCode.OK)
            }
        }

    }
}