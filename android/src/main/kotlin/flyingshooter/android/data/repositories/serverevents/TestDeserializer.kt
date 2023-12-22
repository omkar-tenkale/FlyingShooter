package flyingshooter.android.data.repositories.serverevents

import flyingshooter.core.domain.game.GameRepository
import flyingshooter.shared.data.BaseServerEventData
import flyingshooter.shared.data.TestEvent
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.serialization.json.Json

private val logger = KotlinLogging.logger {}
class TestDeserializer(private val gameRepository: GameRepository) : EventDeserializer {
    override fun deserializeEvent(data: String) {
        val payload = Json.decodeFromString<BaseServerEventData<TestEvent>>(data)
        logger.debug { "GOT "+payload.data.text }
    }
}