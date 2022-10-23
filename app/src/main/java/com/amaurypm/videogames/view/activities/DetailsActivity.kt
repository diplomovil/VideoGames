package com.amaurypm.videogames.view.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.amaurypm.videogames.R
import com.amaurypm.videogames.application.VideoGamesApplication
import com.amaurypm.videogames.databinding.ActivityDetailsBinding
import com.amaurypm.videogames.model.entities.GameEntity
import com.amaurypm.videogames.viewmodel.GameViewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private val gameViewModel: GameViewModel by viewModels() {
        GameViewModel.GameViewModelFactory( (application as VideoGamesApplication).repository )
    }

    var game: GameEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
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

                        tietTitle.inputType = InputType.TYPE_NULL
                        tietGenre.inputType = InputType.TYPE_NULL
                        tietDeveloper.inputType = InputType.TYPE_NULL
                    }

                }

            })
        }

    }

    fun click(view: View) {
        when(view.id){
            R.id.btnEdit -> {
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("ID", game?.id)
                startActivity(intent)
                finish()
            }
            R.id.btnDelete -> {
                //Aquí iría el código para borrar el registro

                AlertDialog.Builder(this)
                    .setTitle("Confirmación")
                    .setMessage("¿Realmente deseas eliminar el juego ${game?.title}?")
                    .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                        try{
                            gameViewModel.deleteGame(game!!)
                            Toast.makeText(this@DetailsActivity, "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@DetailsActivity, MainActivity::class.java))
                            finish()
                        }catch(e: Exception){
                            Toast.makeText(this@DetailsActivity, "No se pudo realizar la eliminación", Toast.LENGTH_SHORT).show()
                        }
                    })
                    .setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                      dialog.dismiss()
                    })
                    .show()

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}