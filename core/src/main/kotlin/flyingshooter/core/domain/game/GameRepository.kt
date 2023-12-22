package flyingshooter.core.domain.game

import flyingshooter.core.domain.game.events.ServerEvent
import flyingshooter.core.domain.game.events.sent.ClientEvent
import flyingshooter.shared.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun observeGameEvents(gameId: GameId) : Flow<Resource<ServerEvent>>
    suspend fun sendGameEvent(gameId: GameId,event: ClientEvent)
}

