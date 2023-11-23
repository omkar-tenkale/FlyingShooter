package flyingshooter.android.presentation.screens.home.game

import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import cafe.adriel.voyager.core.screen.Screen

object GameScreen : Screen {

    @Composable
    override fun Content() {
        val activity = LocalContext.current as FragmentActivity

        Box(Modifier.fillMaxSize().background(Color.Green)) {
            AndroidView(
                modifier = Modifier,
                factory = { context ->
                    FrameLayout(context).apply {
                        id = ViewCompat.generateViewId()
                    }
                },
                update = {
                    activity.supportFragmentManager.findFragmentByTag(GameFragment.TAG)?.let{
//                        activity.supportFragmentManager.commit {
//                            remove(it)
//                        }
                    }
                    activity.supportFragmentManager.commit {
                        add(it.id, GameFragment(), GameFragment.TAG)
                    }
                }
            )
        }
    }
}