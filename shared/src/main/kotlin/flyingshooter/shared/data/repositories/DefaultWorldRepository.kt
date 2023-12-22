package flyingshooter.shared.data.repositories

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.World.Companion.family
import flyingshooter.shared.domain.entities.game.GameCoroutineScope
import flyingshooter.shared.domain.entities.game.base.ECSComponent
import flyingshooter.shared.domain.entities.game.base.components.PositionComponent
import flyingshooter.shared.domain.entities.game.base.systems.MoveSystem
import flyingshooter.shared.domain.entities.game.base.systems.PhysicsSystem
import flyingshooter.shared.domain.entities.game.base.world.WorldRepository
import flyingshooter.shared.domain.entities.game.events.GameEventRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import org.koin.core.scope.Scope

private val logger = KotlinLogging.logger {}

internal class DefaultWorldRepository(
    private val gameEventRepository: GameEventRepository,
    private val dependencyScope: Scope,
    val gameScope: GameCoroutineScope
) : WorldRepository {
    private val world = com.github.quillraven.fleks.configureWorld() {
        injectables {
            add(dependencyScope)
        }

        families {
            val moveFamily = family { all(PositionComponent) }
            onAdd(moveFamily) { entity ->

            }
            onRemove(moveFamily) { entity ->

            }
        }

        systems {
            add(MoveSystem(dependencyScope,gameScope))
            add(PhysicsSystem(dependencyScope))
        }

        onAddEntity { entity ->

        }

        onRemoveEntity { entity ->

        }
    }

    override fun addEntity(entity: Entity) {
        world.entity {

        }
    }

    override fun <T> addComponent(entity: Entity, component: ECSComponent<T>) {

    }

    override fun onTick(durationMs: Float) {
        world.update(durationMs)
    }
}