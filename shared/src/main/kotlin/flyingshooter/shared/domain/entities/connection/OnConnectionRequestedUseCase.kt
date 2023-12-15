package flyingshooter.shared.domain.entities.connection

internal class OnConnectionRequestedUseCase(private val connectionRepository: ConnectionRepository) {
    operator fun invoke(client: Client) = connectionRepository.addConnection(client)
}