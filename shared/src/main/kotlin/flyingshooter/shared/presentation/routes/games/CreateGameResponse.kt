package flyingshooter.shared.presentation.routes.games

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreateGameResponse (
    @SerialName("id") val id: String
)
@Serializable
class ListGameResponse (
    @SerialName("games") val games: List<GameInfo>
){
    @Serializable
    data class GameInfo(@SerialName("id") val id: String, @SerialName("name")val name: String)
}