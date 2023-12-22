package flyingshooter.core

import flyingshooter.core.data.dataModule
import flyingshooter.core.domain.domainModule
import org.koin.dsl.module

val coreModule = module {
    includes(dataModule)
    includes(domainModule)
}