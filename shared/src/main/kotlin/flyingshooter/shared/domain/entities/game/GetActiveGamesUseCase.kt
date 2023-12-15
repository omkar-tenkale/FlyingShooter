package flyingshooter.shared.domain.entities.game

internal class GetActiveGamesUseCase(private val gameServerGameRepository: GameRepository) {
    suspend operator fun invoke() = gameServerGameRepository.getActiveGames()
}