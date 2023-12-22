package flyingshooter.shared.domain.entities.game.base.world

import com.github.quillraven.fleks.Entity
import flyingshooter.shared.domain.entities.game.base.ECSComponent

internal interface WorldRepository {
    fun addEntity(entity: Entity)
    fun <T> addComponent(entity: Entity, component: ECSComponent<T>)
    fun onTick(durationMs: Float)
}