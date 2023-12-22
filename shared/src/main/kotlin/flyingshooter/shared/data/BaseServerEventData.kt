package flyingshooter.shared.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseServerEventData<T>(
    @SerialName("id")
    val id: String,
    @SerialName("type")
    val type: String,
    @SerialName("data")
    val data: T,
)


@Serializable
data class TestEvent(val text: String)