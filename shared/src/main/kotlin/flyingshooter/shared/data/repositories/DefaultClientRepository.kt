package flyingshooter.shared.data.repositories

import flyingshooter.shared.domain.entities.connection.ClientInfo
import flyingshooter.shared.domain.entities.connection.ClientRepository
import flyingshooter.shared.domain.util.NanoId

internal class DefaultClientRepository : ClientRepository {

    private val clients = mutableMapOf<String, ClientInfo>()

    override fun rememberClient(clientInfo: ClientInfo): String {
        return NanoId.generate().also {
            clients[it] = clientInfo
        }
    }
}