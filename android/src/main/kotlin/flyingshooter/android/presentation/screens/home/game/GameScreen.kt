package flyingshooter.android.presentation.screens.home.game

import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

        AndroidView(
            modifier = Modifier,
            factory = { context ->
                FrameLayout(context).apply {
                    id = ViewCompat.generateViewId()
                }
            },
            update = {
                if (activity.supportFragmentManager.findFragmentByTag(GameFragment.TAG) == null) {
                    activity.supportFragmentManager.commit {
                        add(it.id, GameFragment(), GameFragment.TAG)
                    }
                }
            }
        )
    }
}