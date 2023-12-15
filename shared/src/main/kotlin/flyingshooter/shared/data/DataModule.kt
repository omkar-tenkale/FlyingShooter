package flyingshooter.shared.data

import flyingshooter.shared.data.repositories.DefaultClientRepository
import flyingshooter.shared.data.repositories.DefaultGameRepository
import flyingshooter.shared.data.repositories.DefaultSessionRepository
import flyingshooter.shared.domain.entities.connection.ClientRepository
import flyingshooter.shared.domain.entities.connection.SessionRepository
import flyingshooter.shared.domain.entities.game.GameRepository
import org.koin.dsl.module

internal val dataModule = module {
    single<SessionRepository> { DefaultSessionRepository(get()) }
    single<ClientRepository> { DefaultClientRepository() }
    single<GameRepository> { DefaultGameRepository() }
}