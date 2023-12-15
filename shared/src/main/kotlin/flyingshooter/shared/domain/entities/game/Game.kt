package flyingshooter.shared.domain.entities.game

import flyingshooter.shared.domain.util.NanoId
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin

internal abstract class Game{

    val id: String = NanoId.generate()
    abstract val name: String
    private val scope: Scope = getKoin().createScope(id, named<Game>())

    abstract fun begin()

    abstract fun end()

    init {
        scope.declare(this)
    }
}