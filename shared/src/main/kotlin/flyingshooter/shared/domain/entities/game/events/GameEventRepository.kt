package flyingshooter.shared.domain.entities.game.events

import flyingshooter.shared.domain.entities.connection.Client
import kotlinx.coroutines.flow.Flow

internal interface GameEventRepository {
    suspend fun sendEventToConnection(client: Client, event: ServerEvent)
    suspend fun sendEventToAll(event: ServerEvent)
    fun observeGameEvents(clientId: Int): Flow<ServerEvent>
}