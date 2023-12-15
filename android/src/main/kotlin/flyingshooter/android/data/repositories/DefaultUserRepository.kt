package flyingshooter.android.data.repositories

import flyingshooter.android.domain.entities.server.GameServerInfo
import flyingshooter.android.domain.entities.server.GameServerRepository
import flyingshooter.android.domain.entities.user.UserRepository
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@Serializable
data class JwtTokenPayload(
    @SerialName("roles") val roles: List<String>
)
class DefaultUserRepository(private val gameServerRepository: GameServerRepository) :UserRepository{
    @OptIn(ExperimentalEncodingApi::class)
    override fun isUserServerAdmin(): Boolean {
        return gameServerRepository.authToken?.let {
            val payloadBase64 = it.split(".")[1]
            val payloadString = String(Base64.decode(payloadBase64))
            val payload = Json {
                ignoreUnknownKeys = true
            }.decodeFromString<JwtTokenPayload>(payloadString)
            payload.roles.contains("admin")
        } ?: false
    }
}