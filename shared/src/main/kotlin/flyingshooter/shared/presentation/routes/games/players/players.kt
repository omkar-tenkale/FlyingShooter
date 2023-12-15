package flyingshooter.shared.presentation.routes.games.players

import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

internal fun Routing.playersRoute() {
    get("/players") {
        call.respond("Hello, World!")
    }
}