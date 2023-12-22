package flyingshooter.shared.domain.entities.connection

internal interface ConnectionRepository {
    fun getAllConnections(): Set<Client>
    fun addConnection(client: Client)
    fun removeConnection(client: Client)
}