package flyingshooter.android.domain.entities.server

import flyingshooter.android.domain.entities.GameId
import flyingshooter.android.domain.entities.GameInfo
import flyingshooter.android.domain.entities.GameType

interface GameServerRepository {
    var authToken: String?
    suspend fun getGames(): List<GameInfo>
    suspend fun createGame(gameType: GameType): GameId
    suspend fun stopGame(gameId: GameId)
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
