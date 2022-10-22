package com.amaurypm.videogames.view.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.amaurypm.videogames.R
import com.amaurypm.videogames.databinding.ActivityDetailsBinding
import com.amaurypm.videogames.db.DbGames
import com.amaurypm.videogames.model.Game

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    private lateinit var dbGames: DbGames
    var game: Game? = null
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        if(bundle!=null){
            id = bundle.getInt("ID",0)
        }

        dbGames = DbGames(this)

        game = dbGames.getGame(id)

        game?.let{ game ->
            with(binding){
                tietTitle.setText(game.title)
                tietGenre.setText(game.genre)
                tietDeveloper.setText(game.developer)

                //para que se desactive el teclado en los edittext
                tietTitle.inputType = InputType.TYPE_NULL
                tietGenre.inputType = InputType.TYPE_NULL
                tietDeveloper.inputType = InputType.TYPE_NULL
            }
        }
    }

    fun click(view: View) {
        when(view.id){
            R.id.btnEdit -> {
                val intent = Intent(this, EditActivity::class.java)
                intent.putExtra("ID", id)
                startActivity(intent)
                finish()
            }
            R.id.btnDelete -> {
                //Aquí iría el código para borrar el registro

                AlertDialog.Builder(this)
                    .setTitle("Confirmación")
                    .setMessage("¿Realmente deseas eliminar el juego ${game?.title}?")
                    .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                        if(dbGames.deleteGame(id)){
                            Toast.makeText(this@DetailsActivity, "Registro eliminado exitosamente", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@DetailsActivity, MainActivity::class.java))
                            finish()
                        }else{
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