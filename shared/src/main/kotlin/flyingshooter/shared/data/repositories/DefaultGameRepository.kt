package flyingshooter.shared.data.repositories

import flyingshooter.shared.domain.entities.game.GameRepository
import flyingshooter.shared.domain.entities.game.Game

internal class DefaultGameRepository : GameRepository {
    private val games = mutableListOf<Game>()

    override fun getActiveGames() = games
    override fun createGame(game: Game){ games.add(game) }
    override fun startGame(gameId: String) {
        games.firstOrNull { it.id == gameId }?.let {
            it.begin()
        }
    }


    override fun endGame(gameId: String) {
        games.firstOrNull { it.id == gameId }?.let {
            it.end()
            games.remove(it)
        }
    }
}