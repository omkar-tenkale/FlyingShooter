package flyingshooter.shared.domain.entities.game.events


internal class ObserveGameEventsUseCase(private val gameEventRepository: GameEventRepository) {
    operator fun invoke(clientId: Int) = gameEventRepository.observeGameEvents(clientId)
}