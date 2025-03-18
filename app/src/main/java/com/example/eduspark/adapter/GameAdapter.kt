package com.example.eduspark.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eduspark.R
import com.example.eduspark.model.Game
import com.example.eduspark.ui.GameScreen

class GameAdapter(private val gameList: List<Game>): RecyclerView.Adapter<GameAdapter.ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvName = itemView.findViewById<TextView>(R.id.tvName)
        var tvCategory = itemView.findViewById<TextView>(R.id.tvCategory)
        var tvPlayer = itemView.findViewById<TextView>(R.id.tvTotalCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = gameList[position]
        holder.tvName.text = game.name
        holder.tvCategory.text = game.category
        holder.tvPlayer.text = game.totalPlayer.toString() + " players"

        holder.itemView.setOnClickListener {
            val i = Intent(holder.itemView.context, GameScreen::class.java)
            i.putExtra("id", game.id)
            holder.itemView.context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return gameList.size
    }
}