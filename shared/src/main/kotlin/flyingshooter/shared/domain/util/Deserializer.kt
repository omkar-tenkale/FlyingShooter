package flyingshooter.shared.domain.util

import kotlinx.serialization.json.Json

class Deserializer{
    val defaultDeserializer = Json
    val lenientDeserializer = Json { isLenient = true; ignoreUnknownKeys = true }
    inline fun <reified T> decodeFromString(string: String, isLenient: Boolean = false): T {
        return if(isLenient) lenientDeserializer.decodeFromString(string) else defaultDeserializer.decodeFromString(string)
    }
}