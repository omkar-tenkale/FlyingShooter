package flyingshooter.shared.presentation.routes.sessions

import flyingshooter.shared.domain.entities.connection.CreateSessionUseCase
import io.ktor.server.application.call
import io.ktor.server.plugins.origin
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.ktor.plugin.scope

@Serializable
data class CreateSessionRequest(@SerialName("client") val client: ClientInfo)

@Serializable
class ClientInfo(

    @SerialName("androidApp") var androidApp: AndroidApp,

    @SerialName("deviceName") var deviceName: String
) {

    @Serializable
    data class AndroidApp(
        @SerialName("packageName") var packageName: String,

        @SerialName("versionCode") var versionCode: Long,

        @SerialName("sha256CertFingerprints") var sha256CertFingerprints: List<String>,

        @SerialName("installerPackageName") var installerPackageName: String?,

        @SerialName("firstInstallTime") var firstInstallTime: Long,
    )
}

internal fun ClientInfo.toDomainEntity() =
    flyingshooter.shared.domain.entities.connection.ClientInfo(
        androidApp.toDomainEntity(), deviceName
    )

internal fun ClientInfo.AndroidApp.toDomainEntity() =
    flyingshooter.shared.domain.entities.connection.ClientInfo.AndroidApp(
        packageName = packageName,
        versionCode = versionCode,
        sha256CertFingerprints = sha256CertFingerprints,
        installerPackageName = installerPackageName,
        firstInstallTime = firstInstallTime
    )

internal fun Route.sessionsRoute() {
    route("/sessions") {
        post {
            val body = call.receive<CreateSessionRequest>()
            val token = call.scope.get<CreateSessionUseCase>()(
                body.client.toDomainEntity(), call.request.origin.remoteHost
            )
            call.response.headers.append("Authorization", "Bearer $token")
            call.respond("")
        }
    }
}