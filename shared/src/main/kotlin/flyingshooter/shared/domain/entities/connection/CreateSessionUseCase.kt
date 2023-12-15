package flyingshooter.shared.domain.entities.connection

internal class CreateSessionUseCase(private val sessionRepository: SessionRepository) {
    suspend operator fun invoke(clientInfo: ClientInfo,ip: String) = sessionRepository.createSessionToken(clientInfo,ip)
}