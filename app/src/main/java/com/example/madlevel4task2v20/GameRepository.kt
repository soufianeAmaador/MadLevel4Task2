package com.example.madlevel4task2v20

import android.content.Context

class GameRepository (context: Context) {

    private val gameDao: GameDao

    init {
        val database = GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> {
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

    suspend fun countWinnings() : Int{
        return gameDao.countWinnings()
    }

    suspend fun countDraw() : Int{
        return gameDao.countDraw()
    }

    suspend fun countLosses() : Int{
        return gameDao.countLosses()
    }
}