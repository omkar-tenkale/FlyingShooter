package flyingshooter.shared.data.repositories

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.World.Companion.family
import flyingshooter.shared.domain.entities.game.base.ECSComponent
import flyingshooter.shared.domain.entities.game.base.components.PositionComponent
import flyingshooter.shared.domain.entities.game.base.systems.MoveSystem
import flyingshooter.shared.domain.entities.game.base.systems.PhysicsSystem
import flyingshooter.shared.domain.entities.game.base.world.WorldRepository
import flyingshooter.shared.domain.entities.game.events.GameEventRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.koin.core.scope.Scope
import org.slf4j.impl.HandroidLoggerAdapter

private val logger = KotlinLogging.logger {
    HandroidLoggerAdapter.DEBUG = true
}

internal class DefaultWorldRepository(val gameEventRepository: GameEventRepository, private val dependencyScope: Scope) : WorldRepository {
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
            add(MoveSystem())
            add(PhysicsSystem())
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