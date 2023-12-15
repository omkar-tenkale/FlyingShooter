package flyingshooter.shared.domain.entities.game

internal class CreateGameUseCase(private val gameRepository: GameRepository) {
    suspend operator fun invoke(game: Game) = gameRepository.createGame(game)
}