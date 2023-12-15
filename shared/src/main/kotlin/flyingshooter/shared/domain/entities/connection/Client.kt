package flyingshooter.shared.domain.entities.connection

import java.util.concurrent.atomic.AtomicInteger

internal data class Client(
    val id: Int = lastId.getAndIncrement(),
) {
    companion object {
        val lastId = AtomicInteger(0)
    }
}