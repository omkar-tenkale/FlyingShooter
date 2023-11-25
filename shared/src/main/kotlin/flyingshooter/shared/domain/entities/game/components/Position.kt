package flyingshooter.shared.domain.entities.game.components

import flyingshooter.shared.domain.entities.game.base.BaseComponent
import flyingshooter.shared.domain.entities.game.base.BaseComponentType

data class Position(
    var x: Float,
    var y: Float,
) : BaseComponent<Position> {
    override fun type() = Position
    companion object : BaseComponentType<Position>()


}
