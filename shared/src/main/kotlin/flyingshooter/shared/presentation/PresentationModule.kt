package flyingshooter.shared.presentation

import flyingshooter.shared.domain.entities.game.GetActiveGamesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val presentationModule = module {
    singleOf(::GameServer)
}