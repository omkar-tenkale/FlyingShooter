package flyingshooter.shared.data.repositories

import flyingshooter.shared.domain.entities.connection.Client
import flyingshooter.shared.domain.entities.game.events.ServerEvent
import flyingshooter.shared.domain.entities.game.events.GameEventRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

internal class DefaultGameEventRepository() : GameEventRepository {
    private val clientUpdatesFlow = MutableSharedFlow<Pair<Client?,ServerEvent>>()

    override suspend fun sendEventToConnection(client: Client, event: ServerEvent) {
        clientUpdatesFlow.emit(client to event)
    }

    override suspend fun sendEventToAll(event: ServerEvent) {
        clientUpdatesFlow.emit(null to event)
    }

    override fun observeGameEvents(clientId: Int) = clientUpdatesFlow.filter {
        it.first == null || it.first?.id == clientId
    }.map { it.second }
}