package flyingshooter.shared.domain

import flyingshooter.shared.domain.entities.connection.CreateSessionUseCase
import flyingshooter.shared.domain.entities.game.CreateGameUseCase
import flyingshooter.shared.domain.entities.game.GetActiveGamesUseCase
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val domainModule = module {
    singleOf(::CreateSessionUseCase)
    singleOf(::CreateGameUseCase)
    singleOf(::GetActiveGamesUseCase)
}