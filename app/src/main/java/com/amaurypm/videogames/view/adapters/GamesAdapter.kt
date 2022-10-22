package com.amaurypm.videogames.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amaurypm.videogames.databinding.ListElementBinding
import com.amaurypm.videogames.model.Game
import com.amaurypm.videogames.view.activities.MainActivity

/**
 * Creado por Amaury Perea Matsumura el 22/10/22
 */
class GamesAdapter(private val context: Context, val games: ArrayList<Game>): RecyclerView.Adapter<GamesAdapter.ViewHolder>(){

    private val layoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: ListElementBinding): RecyclerView.ViewHolder(view.root){
        val tvTitle = view.tvTitle
        val tvGenre = view.tvGenre
        val tvDeveloper = view.tvDeveloper
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListElementBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvTitle.text = games[position].title
        holder.tvGenre.text = games[position].genre
        holder.tvDeveloper.text = games[position].developer

        //Para los clicks de cada elemento viewholder

        holder.itemView.setOnClickListener {
            //Manejar el click
            if(context is MainActivity) context.selectedGame(games[position])
        }

    }

    override fun getItemCount(): Int {
        return games.size
    }

}