package flyingshooter.android.domain.entities.server

interface LocalServerRepository {
    fun isLocalServer(server: GameServerInfo): Boolean
    fun startServer(): GameServerInfo
    fun getRunningServerInfo(): GameServerInfo?
    fun stopServer()
}

class GetRunningLocalGameServerInfoUseCase(private val localServerRepository: LocalServerRepository) {
    operator fun invoke() = localServerRepository.getRunningServerInfo()
}

class StopLocalGameServerUseCase(private val localServerRepository: LocalServerRepository) {
    operator fun invoke() = localServerRepository.stopServer()
}

class StartLocalGameServerUseCase(private val localServerRepository: LocalServerRepository) {
    operator fun invoke() = localServerRepository.startServer()
}