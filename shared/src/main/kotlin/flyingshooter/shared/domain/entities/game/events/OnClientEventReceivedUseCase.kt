package flyingshooter.shared.domain.entities.game.events

import flyingshooter.shared.domain.entities.connection.Client
import flyingshooter.shared.domain.entities.connection.ConnectionRepository

internal class OnClientEventReceivedUseCase(private val connectionRepository: ConnectionRepository) {
    operator fun invoke(client: Client, serverEvent: ServerEvent) {

    }
}