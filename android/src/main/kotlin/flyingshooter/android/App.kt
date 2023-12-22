package flyingshooter.android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.chibatching.kotpref.Kotpref
import flyingshooter.android.data.dataModule
import flyingshooter.android.domain.domainModule
import flyingshooter.android.presentation.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        startKoin {
            koin.loadModules(listOf(module {
                single { this@startKoin }
            }))
            androidContext(this@App)
            modules(
                domainModule,
                dataModule,
                presentationModule
            )
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}