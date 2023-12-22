package flyingshooter.core.domain.game

class ObserveGameEventUseCase(private val gameRepository: GameRepository) {
    suspend operator fun invoke(gameId: String) = gameRepository.observeGameEvents(gameId)
}