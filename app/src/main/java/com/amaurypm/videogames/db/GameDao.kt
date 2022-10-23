package com.amaurypm.videogames.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.amaurypm.videogames.model.entities.GameEntity
import com.amaurypm.videogames.util.Constants
import kotlinx.coroutines.flow.Flow

/**
 * Creado por Amaury Perea Matsumura el 22/10/22
 */

@Dao
interface GameDao {

    @Insert
    suspend fun insertGame(game: GameEntity)

    @Query("SELECT * FROM ${Constants.DATABASE_GAME_TABLE} ORDER BY ID")
    fun getAllGamesList(): Flow<List<GameEntity>>

    @Query("SELECT * FROM ${Constants.DATABASE_GAME_TABLE} WHERE id = :id LIMIT 1")
    fun getGame(id: Long): Flow<GameEntity>

    @Delete
    suspend fun deleteGame(game: GameEntity)

    @Update
    suspend fun updateGame(game: GameEntity)

}