package com.amaurypm.videogames.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.amaurypm.videogames.model.Game

/**
 * Creado por Amaury Perea Matsumura el 21/10/22
 */
class DbGames(private val context: Context): DbHelper(context) {

    //Aquí se van a implementar las operaciones CRUD (Create, Read, Update and Delete)

    fun insertGame(title: String, genre:String, developer: String): Long{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var id: Long = 0

        try{
            val values = ContentValues()

            values.put("title", title)
            values.put("genre", genre)
            values.put("developer", developer)

            id = db.insert("games", null, values)

        }catch(e: Exception){
            //Manejo de excepción
        }finally {
            db.close()
        }

        return id
    }

    fun getGames(): ArrayList<Game>{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var listGames = ArrayList<Game>()
        var gameTmp: Game? = null
        var cursorGames: Cursor? = null

        cursorGames = db.rawQuery("SELECT * FROM GAMES", null)

        if(cursorGames.moveToFirst()){
            do{
                gameTmp = Game(cursorGames.getInt(0), cursorGames.getString(1), cursorGames.getString(2), cursorGames.getString(3))
                listGames.add(gameTmp)
            }while(cursorGames.moveToNext())
        }

        cursorGames.close()

        return listGames
    }

    fun getGame(id: Int): Game?{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var game: Game? = null

        var cursorGames: Cursor? = null

        cursorGames = db.rawQuery("SELECT * FROM GAMES WHERE id = $id LIMIT 1", null)

        if(cursorGames.moveToFirst()){
            game = Game(cursorGames.getInt(0), cursorGames.getString(1), cursorGames.getString(2), cursorGames.getString(3))
        }

        cursorGames.close()

        return game
    }

    fun updateGame(id: Int, title: String, genre: String, developer: String): Boolean{
        var banderaCorrecto = false

        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("UPDATE GAMES SET title = '$title', genre = '$genre', developer = '$developer' WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){
            //Manejo de la excepción
        }finally {
            db.close()
        }

        return banderaCorrecto
    }

    fun deleteGame(id: Int): Boolean{
        var banderaCorrecto = false

        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("DELETE FROM GAMES WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){

        }finally {
            db.close()
        }

        return banderaCorrecto
    }

}