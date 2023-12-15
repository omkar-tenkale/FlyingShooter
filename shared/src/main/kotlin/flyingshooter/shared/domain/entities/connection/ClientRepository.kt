package flyingshooter.shared.domain.entities.connection

internal interface ClientRepository {
    fun rememberClient(clientInfo: ClientInfo): String
}