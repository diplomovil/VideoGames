package com.amaurypm.videogames.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amaurypm.videogames.model.entities.GameEntity
import com.amaurypm.videogames.util.Constants

/**
 * Creado por Amaury Perea Matsumura el 22/10/22
 */

@Database(entities = [GameEntity::class], version = 1)
abstract class GameRoomDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        @Volatile //lo que se escriba a este campo, será inmediatamente visible a otros hilos (threads)
        private var INSTANCE: GameRoomDatabase? = null

        fun getDatabase(context: Context): GameRoomDatabase {
            //Si la instancia no es nula, entonces se regresa
            //Si es nula, se crea la base de datos (patrón Singleton)
            return INSTANCE ?: synchronized(this) {  //usando el operador elvis
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameRoomDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration() //permite a Room recrear las tablas de la BD si las migraciones para migrar el esquema al más reciente no son encontradas
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }

}