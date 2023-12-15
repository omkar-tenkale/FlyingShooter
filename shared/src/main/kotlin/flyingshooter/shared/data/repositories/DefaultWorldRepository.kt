package flyingshooter.shared.data.repositories

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.github.quillraven.fleks.World.Companion.family
import flyingshooter.shared.domain.entities.game.base.components.PositionComponent
import flyingshooter.shared.domain.entities.game.base.systems.MoveSystem
import flyingshooter.shared.domain.entities.game.base.systems.PhysicsSystem

internal class DefaultWorldRepository {
    private val world = com.github.quillraven.fleks.configureWorld() {
//        injectables {
//            add(b2dWorld)
//        }

        families {
            val moveFamily = family { all(PositionComponent) }
            onAdd(moveFamily) {entity ->

            }
            onRemove(moveFamily) {entity ->

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
}