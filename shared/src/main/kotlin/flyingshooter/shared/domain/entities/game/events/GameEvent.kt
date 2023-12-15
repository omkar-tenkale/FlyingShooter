package flyingshooter.shared.domain.entities.game.events

internal abstract class GameEvent {
    val timeStamp: Long = System.currentTimeMillis()
}