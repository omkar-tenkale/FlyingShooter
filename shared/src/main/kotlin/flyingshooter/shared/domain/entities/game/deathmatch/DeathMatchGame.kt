package flyingshooter.shared.domain.entities.game.deathmatch

import flyingshooter.shared.domain.entities.game.Game
import org.koin.core.scope.Scope

internal class DeathMatchGame() : Game() {
    override val name = "DeathMatch"
    override fun onBegin() {

    }

    override fun onEnd() {

    }
}