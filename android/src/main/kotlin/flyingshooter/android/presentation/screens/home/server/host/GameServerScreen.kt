package flyingshooter.android.presentation.screens.home.server.host

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import flyingshooter.android.domain.entities.server.GetGameRoomsUseCase
import flyingshooter.android.domain.entities.server.EndGameUseCase
import flyingshooter.android.domain.entities.server.GameServerInfo
import flyingshooter.android.domain.entities.server.StopLocalGameServerUseCase
import flyingshooter.android.domain.entities.user.IsUserServerAdminUseCase
import flyingshooter.android.presentation.ResourceState
import flyingshooter.android.presentation.LoadingResource
import flyingshooter.android.presentation.invoke
import flyingshooter.android.presentation.remove
import flyingshooter.android.presentation.screens.home.game.GameScreen
import flyingshooter.android.presentation.updateResource
import flyingshooter.core.domain.game.GameInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin


data class GameServerScreen(val serverScope: Scope) : Screen {

    @Composable
    override fun Content() {
        val serverInfo = serverScope.get<GameServerInfo>()
        val koin = serverScope
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()

        val isUserServerAdmin = LoadingResource<Boolean>()

        fun checkIsServerAdmin() {
            coroutineScope.launch {
                updateResource(isUserServerAdmin) { koin.get<IsUserServerAdminUseCase>()() }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green)
        ) {
            ResourceState(isUserServerAdmin, ::checkIsServerAdmin) {
                Column {
                    if (isUserServerAdmin()) {
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    koin.get<StopLocalGameServerUseCase>().invoke()
                                    withContext(Dispatchers.Main) { navigator.pop() }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Stop Server")
                        }
                    }
                    val games = LoadingResource<List<GameInfo>>()
                    fun fetchGames() {
                        coroutineScope.launch {
                            updateResource(games) { koin.get<GetGameRoomsUseCase>()() }
                        }
                    }
                    LaunchedEffect(Unit) {
                        fetchGames()
                    }
                    ResourceState(games, ::fetchGames) {
                        LazyColumn {
                            items(games()) { game ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = game.name,
                                        modifier = Modifier
                                            .weight(1f)
                                            .clickable {

                                                val gameScope =
                                                    getKoin().createScope<GameInfo>()
                                                gameScope.linkTo(serverScope)
                                                gameScope.declare(game)
                                                navigator.push(GameScreen(gameScope, game))
                                            })
                                    if (isUserServerAdmin()) {
                                        Button(onClick = {
                                            coroutineScope.launch {
                                                koin.get<EndGameUseCase>()
                                                    .invoke(game.id)
                                                games.remove(game)
                                            }
                                        }) {
                                            Text("End Game")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            checkIsServerAdmin()
        }
    }
}
