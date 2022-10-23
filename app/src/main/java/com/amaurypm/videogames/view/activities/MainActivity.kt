package com.amaurypm.videogames.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.amaurypm.videogames.application.VideoGamesApplication
import com.amaurypm.videogames.databinding.ActivityMainBinding
import com.amaurypm.videogames.model.entities.GameEntity
import com.amaurypm.videogames.view.adapters.GamesEntityAdapter
import com.amaurypm.videogames.viewmodel.GameViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val gameViewModel: GameViewModel by viewModels() {
        GameViewModel.GameViewModelFactory( (application as VideoGamesApplication).repository )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gamesEntityAdapter = GamesEntityAdapter(this)
        binding.rvGames.layoutManager = LinearLayoutManager(this)
        binding.rvGames.adapter = gamesEntityAdapter

        gameViewModel.allGamesList.observe(this, Observer { games->

            games?.let{
                if(games.isNotEmpty()){
                    binding.tvSinRegistros.visibility = View.INVISIBLE
                    gamesEntityAdapter.gamesList(games)
                }else{
                    binding.tvSinRegistros.visibility = View.VISIBLE
                }
            }
        })

    }

    fun click(view: View) {
        startActivity(Intent(this, InsertActivity::class.java))
        finish()
    }

    fun selectedGameEntity(game: GameEntity){
        //Manejamos el click del elemento en el recycler view
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("ID", game.id)
        startActivity(intent)
        finish()
    }
}