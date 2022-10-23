package com.amaurypm.videogames.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.amaurypm.videogames.application.VideoGamesApplication
import com.amaurypm.videogames.databinding.ActivityInsertBinding
import com.amaurypm.videogames.model.entities.GameEntity
import com.amaurypm.videogames.viewmodel.GameViewModel

class InsertActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInsertBinding

    private val gameViewModel: GameViewModel by viewModels() {
        GameViewModel.GameViewModelFactory( (application as VideoGamesApplication).repository )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun click(view: View) {
        with(binding){
            when{
                tietTitle.text.toString().isEmpty() -> {
                    tietTitle.error = "No puede quedar vacío"
                    Toast.makeText(this@InsertActivity, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show()
                }
                tietGenre.text.toString().isEmpty() -> {
                    tietGenre.error = "No puede quedar vacío"
                    Toast.makeText(this@InsertActivity, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show()
                }
                tietDeveloper.text.toString().isEmpty() -> {
                    tietDeveloper.error = "No puede quedar vacío"
                    Toast.makeText(this@InsertActivity, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //Realizamos la inserción
                    val game = GameEntity(
                        title = tietTitle.text.toString(),
                        genre = tietGenre.text.toString(),
                        developer = tietDeveloper.text.toString()
                    )

                    try{
                        gameViewModel.insertGame(game)
                        tietTitle.setText("")
                        tietGenre.setText("")
                        tietDeveloper.setText("")
                        tietTitle.requestFocus()
                        Toast.makeText(this@InsertActivity, "Juego guardado exitosamente", Toast.LENGTH_SHORT).show()
                    }catch(e: Exception){
                        Toast.makeText(this@InsertActivity, "Error al guardar el juego", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}