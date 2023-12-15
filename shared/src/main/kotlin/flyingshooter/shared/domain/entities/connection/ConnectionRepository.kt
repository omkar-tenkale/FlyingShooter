package flyingshooter.shared.domain.entities.connection

import flyingshooter.shared.domain.entities.game.events.GameEvent
import kotlinx.coroutines.flow.Flow

internal interface ConnectionRepository {
    fun getAllConnections(): Set<Client>
    fun addConnection(client: Client)
    fun removeConnection(client: Client)
}