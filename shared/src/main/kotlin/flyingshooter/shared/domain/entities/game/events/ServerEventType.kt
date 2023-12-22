package flyingshooter.shared.domain.entities.game.events

import flyingshooter.shared.domain.util.GuardedAgainstObfuscation

@GuardedAgainstObfuscation
enum class ServerEventType {
    MAP_CHANGED;

}