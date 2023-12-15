package flyingshooter.shared.data.repositories

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import flyingshooter.shared.domain.entities.connection.ClientInfo
import flyingshooter.shared.domain.entities.connection.ClientRepository
import flyingshooter.shared.domain.entities.connection.ConnectionRepository
import flyingshooter.shared.domain.entities.connection.SessionRepository
import flyingshooter.shared.domain.util.NanoId
import io.ktor.server.application.ApplicationCall
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException
import java.util.Date
import java.util.concurrent.TimeUnit


internal class DefaultSessionRepository(private val clientRepository: ClientRepository) : SessionRepository {
    fun getLocalIpAddress(): String? {
        return "localhost"
        try {
            val en = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf = en.nextElement()
                val enumIpAddr = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }
        return null
    }

    override fun createSessionToken(clientInfo: ClientInfo,ip: String): String {
        return signAccessToken(clientRepository.rememberClient(clientInfo),ip == getLocalIpAddress())
    }
}

internal object UnauthorizedError : java.lang.IllegalStateException("Unauthorized")

val algorithm: Algorithm by lazy { Algorithm.HMAC256("secret") }

internal fun signAccessToken(clientId: String, isAdmin: Boolean = false): String = JWT.create()
    .withExpiresAt(Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(90)))
    .withIssuedAt(Date())
    .withJWTId(NanoId.generate())
    .withClaim("cid", clientId)
    .let { builder ->
//        user?.let {
//            builder.withClaim("uid", it._id.required().id.toString())
            val roles = mutableListOf<String>()
            isAdmin.takeIf { it }?.let {
                roles.add("admin")
            }
            roles.takeIf { it.isNotEmpty() }?.let {
                builder.withClaim("roles",it)
            }
        builder
//        } ?: builder
    }
    .sign(algorithm)

private val verifier: JWTVerifier = JWT.require(algorithm).build()

internal fun ApplicationCall.jwtTokenBody(): JsonObject? {
    return try {
        val token =
            (request.headers["Authorization"] ?: throw UnauthorizedError).split("Bearer ").last()
        val accessToken = verifier.verify(JWT.decode(token))
        return Json.decodeFromString<JsonObject>(accessToken.payload)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
