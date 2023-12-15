package flyingshooter.android.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import flyingshooter.android.presentation.screens.home.server.host.CreateLocalServerScreen
import kotlinx.coroutines.delay

object HomeScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(key1 = Unit){
            delay(1000)
            navigator.push(CreateLocalServerScreen)
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)){
        }
    }
}