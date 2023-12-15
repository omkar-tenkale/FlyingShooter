package flyingshooter.shared.domain.entities.game.base.components

import flyingshooter.shared.domain.entities.game.base.ECSComponent
import flyingshooter.shared.domain.entities.game.base.ECSComponentType

internal data class PositionComponent(
    var x: Float,
    var y: Float,
) : ECSComponent<PositionComponent> {
    override fun type() = PositionComponent
    companion object : ECSComponentType<PositionComponent>()
}
