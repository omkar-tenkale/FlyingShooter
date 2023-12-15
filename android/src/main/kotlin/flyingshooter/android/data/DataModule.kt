package flyingshooter.android.data

import flyingshooter.android.data.datasource.local.sharedpreferences.DefaultPrefRepository
import flyingshooter.android.data.datasource.local.sharedpreferences.PrefRepository
import flyingshooter.android.data.repositories.DefaultGameServerRepository
import flyingshooter.android.data.repositories.DefaultLocalServerRepository
import flyingshooter.android.data.repositories.DefaultUserRepository
import flyingshooter.android.domain.entities.server.GameServerInfo
import flyingshooter.android.domain.entities.server.GameServerRepository
import flyingshooter.android.domain.entities.server.LocalServerRepository
import flyingshooter.android.domain.entities.user.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val dataModule = module {
    single<PrefRepository> { DefaultPrefRepository(get()) }
    single<LocalServerRepository>{ DefaultLocalServerRepository() }
    scope<GameServerInfo> {
        scoped<GameServerRepository> { DefaultGameServerRepository(get(),get()) }
        scoped<UserRepository> { DefaultUserRepository(get()) }
    }
}