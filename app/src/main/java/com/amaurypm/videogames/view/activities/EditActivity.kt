package com.amaurypm.videogames.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.amaurypm.videogames.application.VideoGamesApplication
import com.amaurypm.videogames.databinding.ActivityEditBinding
import com.amaurypm.videogames.model.entities.GameEntity
import com.amaurypm.videogames.viewmodel.GameViewModel

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    private val gameViewModel: GameViewModel by viewModels() {
        GameViewModel.GameViewModelFactory( (application as VideoGamesApplication).repository )
    }

    var game: GameEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val id = bundle?.getLong("ID", 0)

        if(id != null){
            gameViewModel.getGame(id).observe(this, Observer {

                it?.let{

                    game = it

                    with(binding){
                        tietTitle.setText(it.title)
                        tietGenre.setText(it.genre)
                        tietDeveloper.setText(it.developer)
                    }

                }

            })
        }

    }

    fun click(view: View) {
        with(binding){
            when{
                tietTitle.text.toString().isEmpty() -> {
                    tietTitle.error = "No puede quedar vacío"
                    Toast.makeText(this@EditActivity, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show()
                }
                tietGenre.text.toString().isEmpty() -> {
                    tietGenre.error = "No puede quedar vacío"
                    Toast.makeText(this@EditActivity, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show()
                }
                tietDeveloper.text.toString().isEmpty() -> {
                    tietDeveloper.error = "No puede quedar vacío"
                    Toast.makeText(this@EditActivity, "Por favor llene todos los campos", Toast.LENGTH_SHORT).show()
                }
                else -> {

                    try{
                        val gameTmp = GameEntity(
                            game!!.id,
                            tietTitle.text.toString(),
                            tietGenre.text.toString(),
                            tietDeveloper.text.toString()
                        )

                        gameViewModel.updateGame(gameTmp)
                        Toast.makeText(this@EditActivity, "Registro actualizado exitosamente", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@EditActivity, MainActivity::class.java))
                        finish()
                    }catch (e: Exception){
                        Toast.makeText(this@EditActivity, "Error al actualizar el registro", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, DetailsActivity::class.java))
    }
}