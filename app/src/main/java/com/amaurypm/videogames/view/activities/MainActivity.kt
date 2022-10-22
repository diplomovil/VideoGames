package com.amaurypm.videogames.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.amaurypm.videogames.databinding.ActivityMainBinding
import com.amaurypm.videogames.db.DbGames
import com.amaurypm.videogames.db.DbHelper
import com.amaurypm.videogames.model.Game
import com.amaurypm.videogames.view.adapters.GamesAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var listGames: ArrayList<Game>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /*
        //Código para probar que se generó correctamente la BD
        val dbHelper = DbHelper(this)
        val db = dbHelper.writableDatabase
        if(db!=null){
            Toast.makeText(this, "La BD fue creada exitosamente", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Error al crear la BD", Toast.LENGTH_SHORT).show()
        }*/

        val dbGames = DbGames(this)

        listGames = dbGames.getGames()

        val gamesAdapter = GamesAdapter(this, listGames)
        binding.rvGames.layoutManager = LinearLayoutManager(this)
        binding.rvGames.adapter = gamesAdapter

        if(listGames.size == 0) binding.tvSinRegistros.visibility = View.VISIBLE
        else binding.tvSinRegistros.visibility = View.INVISIBLE

    }

    fun click(view: View) {
        startActivity(Intent(this, InsertActivity::class.java))
        finish()
    }

    fun selectedGame(game: Game){
        //Manejamos el click del elemento en el recycler view
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("ID", game.id)
        startActivity(intent)
        finish()
    }
}