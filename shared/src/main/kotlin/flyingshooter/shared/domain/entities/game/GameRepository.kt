package flyingshooter.shared.domain.entities.game


internal interface GameRepository {
    fun getActiveGames(): List<Game>
    fun createGame(game: Game)
    fun startGame(gameId: String)
    fun endGame(gameId: String)
}