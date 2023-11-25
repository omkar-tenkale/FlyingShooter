package flyingshooter.shared.domain.entities.game.components

import flyingshooter.shared.domain.entities.game.base.BaseComponent
import flyingshooter.shared.domain.entities.game.base.BaseComponentType

data class Sprite(
    var texturePath: String
) : BaseComponent<Sprite> {
    override fun type() = Sprite

    companion object : BaseComponentType<Sprite>()
}