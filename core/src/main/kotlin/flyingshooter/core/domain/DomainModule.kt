package flyingshooter.core.domain

import flyingshooter.core.domain.game.GameInfo
import flyingshooter.core.domain.game.ObserveGameEventUseCase
import org.koin.core.module.dsl.scopedOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val domainModule = module {
    scope<GameInfo> {
        scopedOf(::ObserveGameEventUseCase)
    }
}