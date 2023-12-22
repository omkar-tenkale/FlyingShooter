package flyingshooter.shared.domain.entities.game.events

import flyingshooter.shared.domain.entities.connection.Client
import kotlinx.coroutines.flow.Flow

internal interface GameEventRepository {
    suspend fun sendEventToConnection(client: Client, event: GameEvent)
    suspend fun sendEventToAll(event: GameEvent)
    fun observeGameEvents(clientId: Int): Flow<GameEvent>
}