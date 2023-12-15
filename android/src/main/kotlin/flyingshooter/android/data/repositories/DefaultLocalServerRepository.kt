package flyingshooter.android.data.repositories

import flyingshooter.android.domain.entities.server.GameServerInfo
import flyingshooter.android.domain.entities.server.LocalServerRepository
import flyingshooter.shared.presentation.GameServer

class DefaultLocalServerRepository : LocalServerRepository {
    private var localServer: GameServer? = null
    private val localServerInfo = GameServerInfo("Local", "127.0.0.1", 5383)
    override fun isLocalServer(serverInfo: GameServerInfo): Boolean {
        return serverInfo.host == localServerInfo.host
    }

    override fun startServer(): GameServerInfo {
        getRunningServerInfo()?.let {
            error("Running server needs to be stopped before attempting to start again")
        }
        localServer = GameServer().also { it.start() }
        return localServerInfo
    }

    override fun getRunningServerInfo() = localServer?.let { localServerInfo }

    override fun stopServer() {
        localServer?.stop()
        localServer = null
    }
}
