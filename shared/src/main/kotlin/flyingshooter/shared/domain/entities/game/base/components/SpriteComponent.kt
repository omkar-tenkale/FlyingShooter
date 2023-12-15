package flyingshooter.shared.domain.entities.game.base.components

import flyingshooter.shared.domain.entities.game.base.ECSComponent
import flyingshooter.shared.domain.entities.game.base.ECSComponentType

internal data class SpriteComponent(
    var texturePath: String
) : ECSComponent<SpriteComponent> {
    override fun type() = SpriteComponent

    companion object : ECSComponentType<SpriteComponent>()
}