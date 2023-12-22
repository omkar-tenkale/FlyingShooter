package flyingshooter.core

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ExtendViewport
import flyingshooter.core.domain.game.GameInfo
import flyingshooter.core.domain.game.ObserveGameEventUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.async.KtxAsync
import ktx.graphics.use
import org.koin.core.scope.Scope

class FlyingShooterGame(val gameScope: Scope) : KtxGame<KtxScreen>() {
    override fun create() {
        KtxAsync.initiate()

        addScreen(FirstScreen(gameScope))
        setScreen<FirstScreen>()
    }
}

class FirstScreen(gameScope: Scope) : KtxScreen {
    private val image = Texture("logo.png".toInternalFile(), true).apply { setFilter(Linear, Linear) }
    private val batch by lazy{ SpriteBatch() }
    val gameStage by lazy { Stage(ExtendViewport(16f, 9f), batch) }

    init {
        GlobalScope.launch {
            gameScope.get<ObserveGameEventUseCase>()(gameScope.get<GameInfo>().id).launchIn(GlobalScope)
        }
    }
    override fun render(delta: Float) {
        clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)
        batch.use {
            it.draw(image, 100f, 160f)
        }
    }

    override fun dispose() {
        image.disposeSafely()
        batch.disposeSafely()
    }
}
