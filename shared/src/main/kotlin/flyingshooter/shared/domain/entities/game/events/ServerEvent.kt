package flyingshooter.shared.domain.entities.game.events

import kotlinx.serialization.Serializable

@Serializable
internal data class ServerEvent(
    val text: String,
)