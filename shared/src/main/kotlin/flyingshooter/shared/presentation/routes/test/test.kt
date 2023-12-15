package flyingshooter.shared.presentation.routes.test

import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

internal fun Route.healthRoute() {
    get("/ping") {
        call.respond("pong")
    }
    get("/echo") {
        val request = call.receive<String>()
        call.respond(request)
    }
}