package flyingshooter.android.domain.entities.server

import flyingshooter.android.domain.entities.GameId
import flyingshooter.android.domain.entities.GameInfo
import flyingshooter.android.domain.entities.GameType
import flyingshooter.android.domain.entities.game.events.recieved.ServerEvent
import flyingshooter.android.domain.entities.game.events.sent.ClientEvent
import kotlinx.coroutines.flow.Flow

interface GameServerRepository {
    var authToken: String?
    suspend fun getGames(): List<GameInfo>
    suspend fun createGame(gameType: GameType): GameId
    suspend fun stopGame(gameId: GameId)
    suspend fun observeGameEvents(gameId: GameId) : Flow<ServerEvent>
    suspend fun sendGameEvent(gameId: GameId,event: ClientEvent)
}


class CreateGameUseCase(private val gameServerRepository: GameServerRepository) {
    suspend operator fun invoke(gameType: GameType) = gameServerRepository.createGame(gameType)
}
class EndGameUseCase(private val gameServerRepository: GameServerRepository) {
    suspend operator fun invoke(serverInfo: GameServerInfo, gameId: GameId) = gameServerRepository.stopGame(gameId)
}
class GetGameRoomsUseCase(private val gameServerRepository: GameServerRepository) {
    suspend operator fun invoke(serverInfo: GameServerInfo) = gameServerRepository.getGames()
}

class SendGameEventUseCase(private val gameServerRepository: GameServerRepository) {
    suspend operator fun invoke(gameId: String, event: ClientEvent) = gameServerRepository.sendGameEvent(gameId,event)
}

class ObserveGameEventUseCase(private val gameServerRepository: GameServerRepository) {
    suspend operator fun invoke(gameId: String) = gameServerRepository.observeGameEvents(gameId)
}
