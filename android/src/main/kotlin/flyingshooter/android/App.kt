package flyingshooter.android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.chibatching.kotpref.Kotpref
import flyingshooter.android.data.dataModule
import flyingshooter.android.domain.domainModule
import flyingshooter.android.presentation.presentationModule
import flyingshooter.core.coreModule
import flyingshooter.shared.domain.util.LoggingInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin
import org.slf4j.impl.HandroidLoggerAdapter

class App : Application() {
    override fun onCreate() {
        super.onCreate()


        Kotpref.init(this)
        initKoinDI()
        getKoin().get<LoggingInitializer>().init()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun initKoinDI() {
        startKoin {
            koin.loadModules(listOf(module {
                single { this@startKoin }
                single<LoggingInitializer> {
                    object : LoggingInitializer {
                        override fun init() {
                            HandroidLoggerAdapter.DEBUG = true
                        }
                    }
                }
            }))
            androidContext(this@App)
            modules(
                domainModule,
                dataModule,
                presentationModule,
                coreModule
            )
        }
    }
}