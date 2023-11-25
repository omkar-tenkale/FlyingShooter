package flyingshooter.android.presentation.screens.home.game

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
    ): View? = initializeForView(FlyingShooterGame(), AndroidApplicationConfiguration())

    companion object {
        val TAG: String = this::class.java.canonicalName
    }
}