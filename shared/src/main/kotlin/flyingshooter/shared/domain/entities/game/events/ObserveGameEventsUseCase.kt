package flyingshooter.shared.domain.entities.game.events

import flyingshooter.shared.domain.entities.connection.Client

internal class ObserveGameEventsUseCase(private val gameEventRepository: GameEventRepository) {
    operator fun invoke() = gameEventRepository.observeGameEvents()
}