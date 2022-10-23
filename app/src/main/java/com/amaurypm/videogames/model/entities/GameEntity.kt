package com.amaurypm.videogames.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amaurypm.videogames.util.Constants

/**
 * Creado por Amaury Perea Matsumura el 22/10/22
 */

@Entity(tableName = Constants.DATABASE_GAME_TABLE)
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id: Long = 0,
    @ColumnInfo val title: String,
    @ColumnInfo val genre: String,
    @ColumnInfo val developer: String
)