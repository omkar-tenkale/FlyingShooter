package flyingshooter.shared.domain.entities.game.base

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

internal interface ECSComponent<T> : Component<T>{

}

internal abstract class ECSComponentType<T>: ComponentType<T>() {

}