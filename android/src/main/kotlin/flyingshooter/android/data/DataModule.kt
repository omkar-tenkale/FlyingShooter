package flyingshooter.android.data

import flyingshooter.android.data.datasource.local.sharedpreferences.DefaultPrefRepository
import flyingshooter.android.data.datasource.local.sharedpreferences.PrefRepository
import org.koin.dsl.module


val dataModule = module {
    single<PrefRepository> { DefaultPrefRepository(get()) }
}