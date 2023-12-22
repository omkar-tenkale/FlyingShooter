package flyingshooter.shared.domain.entities.game

import flyingshooter.shared.domain.entities.game.base.world.WorldRepository
import flyingshooter.shared.domain.util.NanoId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin
import kotlin.coroutines.CoroutineContext

class GameCoroutineScope(override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()) : CoroutineScope

internal abstract class Game() {

    val id: String = NanoId.generate()
    protected val coroutineScope: GameCoroutineScope by lazy { GameCoroutineScope() }
    protected val scope: Scope = getKoin().createScope(id, named<Game>()).apply {
        declare(this)
        declare(coroutineScope)
    }
    inline fun <reified T> get(): T = scope.get()

    abstract val name: String

    val SERVER_TICK_RATE_HZ = 20
    val durationBetweenServerTicks by lazy { 1000f/SERVER_TICK_RATE_HZ }
    fun begin() {
        onBegin()
        val worldRepository = get<WorldRepository>()
        coroutineScope.launch {
            worldRepository.onTick(0f)
            while (isActive){
                delay(durationBetweenServerTicks.toLong())
                worldRepository.onTick(durationBetweenServerTicks)
            }
        }
    }

    abstract fun onBegin()

    fun end() {
        try {
            onEnd()

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            coroutineScope.cancel()
        }
    }

    abstract fun onEnd()
}