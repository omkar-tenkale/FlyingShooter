package flyingshooter.shared.domain

import flyingshooter.shared.data.repositories.DefaultConnectionRepository
import flyingshooter.shared.data.repositories.DefaultGameEventRepository
import flyingshooter.shared.data.repositories.DefaultWorldRepository
import flyingshooter.shared.domain.entities.connection.ConnectionRepository
import flyingshooter.shared.domain.entities.connection.CreateSessionUseCase
import flyingshooter.shared.domain.entities.game.CreateGameUseCase
import flyingshooter.shared.domain.entities.game.EndGameUseCase
import flyingshooter.shared.domain.entities.game.Game
import flyingshooter.shared.domain.entities.game.GetActiveGamesUseCase
import flyingshooter.shared.domain.entities.game.base.world.WorldRepository
import flyingshooter.shared.domain.entities.game.events.GameEventRepository
import flyingshooter.shared.domain.entities.game.events.ObserveGameEventsUseCase
import flyingshooter.shared.domain.entities.game.events.OnClientEventReceivedUseCase
import flyingshooter.shared.domain.util.Deserializer
import flyingshooter.shared.presentation.GameServer
import org.koin.core.module.dsl.scopedOf
import org.koin.dsl.module

internal val domainModule = module {
    single{ Deserializer() }
    scope<GameServer> {
        scopedOf(::CreateSessionUseCase)
        scopedOf(::CreateGameUseCase)
        scopedOf(::EndGameUseCase)
        scopedOf(::GetActiveGamesUseCase)
    }
    scope<Game> {
        scopedOf(::ObserveGameEventsUseCase)
        scopedOf(::OnClientEventReceivedUseCase)
        scoped<WorldRepository> { DefaultWorldRepository(get(),get(),get()) }
        scoped<GameEventRepository> { DefaultGameEventRepository() }
        scoped<ConnectionRepository> { DefaultConnectionRepository() }
    }
}