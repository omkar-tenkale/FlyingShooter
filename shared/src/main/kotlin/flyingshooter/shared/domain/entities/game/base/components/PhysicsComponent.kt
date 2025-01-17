package flyingshooter.shared.domain.entities.game.base.components

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.World
import com.badlogic.gdx.physics.box2d.World as Box2DWorld
import flyingshooter.shared.domain.entities.game.base.ECSComponent
import flyingshooter.shared.domain.entities.game.base.ECSComponentType

internal class PhysicsComponent : ECSComponent<PhysicsComponent> {

    override fun type() = PhysicsComponent
    companion object : ECSComponentType<PhysicsComponent>()

    lateinit var body: Body

    override fun World.onAdd(entity: Entity) {
        body = inject<Box2DWorld>().createBody(BodyDef())
        body.userData = entity
    }

    override fun World.onRemove(entity: Entity) {
        body.world.destroyBody(body)
        body.userData = null
    }
}