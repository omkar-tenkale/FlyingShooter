package flyingshooter.shared.domain.entities.game

internal class EndGameUseCase(private val gameRepository: GameRepository) {
    suspend operator fun invoke(gameId: String){
        gameRepository.endGame(gameId)
    }
}