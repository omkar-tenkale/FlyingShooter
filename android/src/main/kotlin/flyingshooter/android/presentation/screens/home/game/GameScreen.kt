package flyingshooter.android.presentation.screens.home.game

import android.util.Log
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import cafe.adriel.voyager.core.screen.Screen
import flyingshooter.android.domain.entities.GameInfo
import org.koin.core.scope.Scope
import kotlin.math.atan2
import kotlin.math.log

class GameScreen(val serverScope: Scope, game: GameInfo) : Screen {

    @Composable
    override fun Content() {
        val activity = LocalContext.current as FragmentActivity

        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Green)
        ) {
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    FrameLayout(context).apply {
                        id = ViewCompat.generateViewId()
                    }
                },
                update = {
                    activity.supportFragmentManager.findFragmentByTag(GameFragment.TAG)?.let {
//                        activity.supportFragmentManager.commit {
//                            remove(it)
//                        }
                    }
                    activity.supportFragmentManager.commit {
                        add(it.id, GameFragment(), GameFragment.TAG)
                    }
                }
            )
            Controls()
        }
    }

    @Composable
    fun Controls() {
        Box(modifier = Modifier.fillMaxSize()){
            JoyStick(modifier = Modifier.alpha(0.5f).padding(60.dp).align(Alignment.BottomStart)){ x: Float, y: Float ->
                Log.d("Controller","x $x y $y Angle ${calculateAngle(x,y)}")
            }
            JoyStick(modifier = Modifier.alpha(0.5f).padding(60.dp).align(Alignment.BottomEnd)){ x: Float, y: Float ->
                Log.d("Controller","x $x y $y")
            }
        }
    }
}

fun calculateAngle(x: Float, y: Float): Float {
    // Calculate the angle in radians using atan2
    val angleRadians = atan2(y, x)

    // Convert radians to degrees
    val angleDegrees = Math.toDegrees(angleRadians.toDouble()).toFloat()

    // Ensure the angle is positive (between 0 and 360 degrees)
    return if (angleDegrees < 0) angleDegrees + 360 else angleDegrees
}