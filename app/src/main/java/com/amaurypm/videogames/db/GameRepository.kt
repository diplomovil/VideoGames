package com.amaurypm.videogames.db

import androidx.annotation.WorkerThread
import com.amaurypm.videogames.model.entities.GameEntity
import kotlinx.coroutines.flow.Flow

/**
 * Creado por Amaury Perea Matsumura el 22/10/22
 */
class GameRepository(private val gameDao: GameDao) {

    @WorkerThread
    suspend fun insertGameData(game: GameEntity){
        gameDao.insertGame(game)
    }

    val allGamesList: Flow<List<GameEntity>> = gameDao.getAllGamesList()

    fun getGame(id: Long): Flow<GameEntity> = gameDao.getGame(id)

    @WorkerThread
    suspend fun deleteGame(game: GameEntity){
        gameDao.deleteGame(game)
    }

    @WorkerThread
    suspend fun updateGame(game: GameEntity){
        gameDao.updateGame(game)
    }

}