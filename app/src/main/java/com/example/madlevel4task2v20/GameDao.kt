package com.example.madlevel4task2v20

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
public interface GameDao{

    @Query("SELECT * FROM GameHistoryTable")
    suspend fun getAllGames(): List<Game>

    @Insert
    suspend fun insertGame(game: Game)


    @Query("DELETE FROM GameHistoryTable")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(*) FROM GameHistoryTable Where result = 'USER_WINS'")
    suspend fun countWinnings(): Int

    @Query("SELECT COUNT(*) FROM GameHistoryTable Where result = 'DRAW'")
    suspend fun countDraw(): Int

    @Query("SELECT COUNT(*) FROM GameHistoryTable Where result = 'COMPUTER_WINS'")
    suspend fun countLosses(): Int


}