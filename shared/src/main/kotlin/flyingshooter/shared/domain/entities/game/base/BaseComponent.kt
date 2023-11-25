package flyingshooter.shared.domain.entities.game.base

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

interface BaseComponent<T> : Component<T>{

}

abstract class BaseComponentType<T>: ComponentType<T>() {

}