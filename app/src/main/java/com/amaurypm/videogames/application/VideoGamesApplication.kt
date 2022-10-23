package com.amaurypm.videogames.application

import android.app.Application
import com.amaurypm.videogames.db.GameRepository
import com.amaurypm.videogames.db.GameRoomDatabase

/**
 * Creado por Amaury Perea Matsumura el 22/10/22
 */
class VideoGamesApplication: Application() {
    //Para cargar la base de datos con lazy (que se cargue cuando se requiera)
    private val database by lazy{ GameRoomDatabase.getDatabase(this@VideoGamesApplication) }

    //Para el repositorio también con lazy
    val repository by lazy { GameRepository(database.gameDao()) }
}