package flyingshooter.android.presentation.screens.home.server.host

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import flyingshooter.android.domain.entities.GameType
import flyingshooter.android.domain.entities.server.GetRunningLocalGameServerInfoUseCase
import flyingshooter.android.domain.entities.server.CreateGameUseCase
import flyingshooter.android.domain.entities.server.GameServerInfo
import flyingshooter.android.domain.entities.server.StartLocalGameServerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object CreateLocalServerScreen : Screen {

    @Composable
    override fun Content() {
        val koin = org.koin.androidx.compose.getKoin()
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        Button(
            onClick = {
                coroutineScope.launch {
                    val serverInfo = koin.get<GetRunningLocalGameServerInfoUseCase>()()
                        ?: koin.get<StartLocalGameServerUseCase>()()

                    val serverScope = koin.createScope<GameServerInfo>()
                    serverScope.declare(serverInfo)
                    val gameInfo = serverScope.get<CreateGameUseCase>().invoke(GameType.DEATH_MATCH)
                    withContext(Dispatchers.Main){
                        navigator.push(GameServerScreen(serverScope))
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Start Server")
        }
    }
}