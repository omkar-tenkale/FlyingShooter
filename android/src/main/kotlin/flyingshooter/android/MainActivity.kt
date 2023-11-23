package flyingshooter.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import flyingshooter.android.presentation.screens.home.HomeScreen
import flyingshooter.android.presentation.screens.home.game.GameScreen
import flyingshooter.core.FlyingShooterGame

/** Launches the Android application. */
class MainActivity : FragmentActivity(), AndroidFragmentApplication.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Navigator(HomeScreen)
        }
    }

    override fun exit() {
        println()
    }
}