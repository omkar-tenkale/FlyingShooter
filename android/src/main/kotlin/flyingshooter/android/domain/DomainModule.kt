package flyingshooter.android.domain

import flyingshooter.android.domain.entities.server.GetRunningLocalGameServerInfoUseCase
import flyingshooter.android.domain.entities.server.CreateGameUseCase
import flyingshooter.android.domain.entities.server.EndGameUseCase
import flyingshooter.android.domain.entities.server.GameServerInfo
import flyingshooter.android.domain.entities.server.GetGameRoomsUseCase
import flyingshooter.android.domain.entities.server.StartLocalGameServerUseCase
import flyingshooter.android.domain.entities.server.StopLocalGameServerUseCase
import flyingshooter.android.domain.entities.user.IsUserServerAdminUseCase
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetRunningLocalGameServerInfoUseCase)
    singleOf(::StartLocalGameServerUseCase)
    singleOf(::StopLocalGameServerUseCase)
    scope<GameServerInfo> {
        scopedOf(::CreateGameUseCase)
        scopedOf(::IsUserServerAdminUseCase)
        scopedOf(::GetGameRoomsUseCase)
        scopedOf(::EndGameUseCase)
    }
}