package flyingshooter.shared.domain

import flyingshooter.shared.data.repositories.DefaultGameEventRepository
import flyingshooter.shared.data.repositories.DefaultWorldRepository
import flyingshooter.shared.domain.entities.connection.CreateSessionUseCase
import flyingshooter.shared.domain.entities.game.CreateGameUseCase
import flyingshooter.shared.domain.entities.game.EndGameUseCase
import flyingshooter.shared.domain.entities.game.Game
import flyingshooter.shared.domain.entities.game.GetActiveGamesUseCase
import flyingshooter.shared.domain.entities.game.base.world.WorldRepository
import flyingshooter.shared.domain.entities.game.events.GameEventRepository
import flyingshooter.shared.presentation.GameServer
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.dsl.scoped

internal val domainModule = module {
    scope<GameServer> {
        scopedOf(::CreateSessionUseCase)
        scopedOf(::CreateGameUseCase)
        scopedOf(::EndGameUseCase)
        scopedOf(::GetActiveGamesUseCase)
    }
    scope<Game> {
        scoped<WorldRepository> { DefaultWorldRepository(get(),get()) }
        scoped<GameEventRepository> { DefaultGameEventRepository() }
    }
}