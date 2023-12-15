package flyingshooter.shared.domain.entities.connection

internal interface SessionRepository {
    fun createSessionToken(clientInfo: ClientInfo,ip: String): String
}