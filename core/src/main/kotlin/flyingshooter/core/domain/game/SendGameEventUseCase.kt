package flyingshooter.core.domain.game

import flyingshooter.core.domain.game.events.sent.ClientEvent

class SendGameEventUseCase(private val gameRepository: GameRepository) {
    suspend operator fun invoke(gameId: String, event: ClientEvent) = gameRepository.sendGameEvent(gameId,event)
}