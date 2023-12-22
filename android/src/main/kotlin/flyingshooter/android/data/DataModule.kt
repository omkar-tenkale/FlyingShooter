package flyingshooter.android.data

import flyingshooter.android.data.datasource.local.sharedpreferences.DefaultPrefRepository
import flyingshooter.android.data.datasource.local.sharedpreferences.PrefRepository
import flyingshooter.android.data.repositories.DefaultGameRepository
import flyingshooter.android.data.repositories.DefaultGameServerRepository
import flyingshooter.android.data.repositories.DefaultLocalServerRepository
import flyingshooter.android.data.repositories.DefaultUserRepository
import flyingshooter.core.domain.game.GameRepository
import flyingshooter.android.domain.entities.server.GameServerInfo
import flyingshooter.android.domain.entities.server.GameServerRepository
import flyingshooter.android.domain.entities.server.LocalServerRepository
import flyingshooter.android.domain.entities.user.UserRepository
import flyingshooter.core.domain.game.GameInfo
import org.koin.dsl.module


val dataModule = module {
    single<PrefRepository> { DefaultPrefRepository(get()) }
    single<LocalServerRepository>{ DefaultLocalServerRepository() }
    scope<GameServerInfo> {
        scoped<UserRepository> { DefaultUserRepository(get()) }
        scoped<DefaultGameServerRepository> { DefaultGameServerRepository(get(),get()) }
        scoped<GameServerRepository> { get<DefaultGameServerRepository>()  }
    }
    scope<GameInfo> {
        scoped<GameRepository> { DefaultGameRepository(get(),get(),get(),get()) }
    }
}