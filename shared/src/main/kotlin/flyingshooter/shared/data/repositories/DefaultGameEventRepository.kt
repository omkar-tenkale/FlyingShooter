package flyingshooter.shared.data.repositories

import flyingshooter.shared.domain.entities.connection.Client
import flyingshooter.shared.domain.entities.game.events.GameEvent
import flyingshooter.shared.domain.entities.game.events.GameEventRepository
import kotlinx.coroutines.flow.MutableSharedFlow

internal class DefaultGameEventRepository(
    private val gameEventRepository: GameEventRepository
) : GameEventRepository {
    private val clientUpdatesFlow = MutableSharedFlow<Pair<Client,GameEvent>>()

    override suspend fun sendEventToConnection(client: Client, event: GameEvent) {
        gameEventRepository.sendEventToConnection(client, event)
        clientUpdatesFlow.emit(client to event)
    }

    override fun observeGameEvents() = clientUpdatesFlow
}