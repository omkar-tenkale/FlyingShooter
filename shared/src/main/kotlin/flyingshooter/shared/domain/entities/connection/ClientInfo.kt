package flyingshooter.shared.domain.entities.connection

internal class ClientInfo(
    private val androidApp: AndroidApp,
    private val deviceName: String,
){
    data class AndroidApp(
        private val packageName: String,
        private val versionCode: Long,
        private val sha256CertFingerprints: List<String>,
        private val installerPackageName: String?,
        private val firstInstallTime: Long,
    )
}




