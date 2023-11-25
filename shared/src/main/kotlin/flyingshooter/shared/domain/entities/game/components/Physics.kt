package flyingshooter.shared.domain.entities.game.components

import com.badlogic.gdx.physics.box2d.Body
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.World
import com.badlogic.gdx.physics.box2d.World as Box2DWorld
import flyingshooter.shared.domain.entities.game.base.BaseComponent
import flyingshooter.shared.domain.entities.game.base.BaseComponentType

class PhysicsComponent : BaseComponent<PhysicsComponent> {

    override fun type() = PhysicsComponent
    companion object : BaseComponentType<PhysicsComponent>()

    lateinit var body: Body

    override fun World.onAdd(entity: Entity) {
        body = inject<Box2DWorld>().createBody( /* body creation code omitted */)
        body.userData = entity
    }

    override fun World.onRemove(entity: Entity) {
        body.world.destroyBody(body)
        body.userData = null
    }
}