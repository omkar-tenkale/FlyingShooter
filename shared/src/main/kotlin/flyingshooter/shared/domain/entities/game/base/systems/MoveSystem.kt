package flyingshooter.shared.domain.entities.game.base.systems

import com.github.quillraven.fleks.Fixed
import com.github.quillraven.fleks.IntervalSystem
import flyingshooter.shared.domain.entities.connection.ConnectionRepository
import flyingshooter.shared.domain.entities.game.GameCoroutineScope
import flyingshooter.shared.domain.entities.game.events.GameEventRepository
import flyingshooter.shared.domain.entities.game.events.ServerEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.scope.Scope
import kotlin.random.Random

internal class MoveSystem(dependencyScope: Scope,val gameScope: GameCoroutineScope) : IntervalSystem(Fixed(1000f)) {

    private val gameEventRepository: GameEventRepository by dependencyScope.inject()
    override fun onTick() {
        gameScope.launch {
            gameEventRepository.sendEventToAll(ServerEvent("Hello from server ${Random.nextInt(1000,50000)}"))
        }
    }
}