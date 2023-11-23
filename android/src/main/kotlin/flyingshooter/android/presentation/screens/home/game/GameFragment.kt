package flyingshooter.android.presentation.screens.home.game

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.AndroidFragmentApplication
import flyingshooter.core.FlyingShooterGame


class GameFragment : AndroidFragmentApplication(),AndroidFragmentApplication.Callbacks {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initializeForView(FlyingShooterGame(), AndroidApplicationConfiguration().apply {
        useImmersiveMode = true
    })

    companion object {
        val TAG: String = this::class.java.canonicalName
    }

}