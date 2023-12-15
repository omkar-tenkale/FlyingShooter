package flyingshooter.android.domain.entities.user

import flyingshooter.android.domain.entities.server.GameServerInfo

interface UserRepository {
    fun isUserServerAdmin(): Boolean
}


class IsUserServerAdminUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.isUserServerAdmin()
}
