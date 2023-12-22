package flyingshooter.android.data.repositories.serverevents

import flyingshooter.core.domain.game.GameRepository
import flyingshooter.shared.domain.entities.game.events.ServerEventType
import flyingshooter.shared.domain.util.required
import org.koin.java.KoinJavaComponent.getKoin

interface ServerEventsDeserializationHandler{
    fun handle(type: ServerEventType, data: String)
}

interface EventDeserializer{
    fun deserializeEvent(data: String)
}

class DefaultServerEventsDeserializationHandler(gameRepository: GameRepository): ServerEventsDeserializationHandler{
    private val registry = mutableMapOf<ServerEventType,EventDeserializer>()

    init {
        ServerEventType.entries.forEach {
            register(
                it, when (it) {
                    ServerEventType.MAP_CHANGED -> TestDeserializer(gameRepository)
                }
            )
        }
    }

    private fun register(eventType: ServerEventType, deserializer: EventDeserializer) {
        registry[eventType] = deserializer
    }
    override fun handle(type: ServerEventType, data: String) {
        registry[type].required().deserializeEvent(data)
    }
}