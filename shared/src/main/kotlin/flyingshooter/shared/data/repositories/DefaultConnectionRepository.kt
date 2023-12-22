package flyingshooter.shared.data.repositories

import flyingshooter.shared.domain.entities.connection.Client
import flyingshooter.shared.domain.entities.connection.ConnectionRepository
import java.util.Collections

internal class DefaultConnectionRepository: ConnectionRepository {
    private val clients = Collections.synchronizedSet<Client>(LinkedHashSet())

    override fun getAllConnections(): Set<Client> = clients

    override fun addConnection(client: Client) {
        clients.add(client)
    }

    override fun removeConnection(client: Client) {
        clients.remove(client)
    }
}