package com.example.eduspark.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eduspark.R
import com.example.eduspark.model.Game
import com.example.eduspark.model.Leaderboard

class LeaderboardAdapter(private val leaderboardList: List<Leaderboard>): RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    class  ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNickName = itemView.findViewById<TextView>(R.id.tvNickName)
        var tvTotalPoint = itemView.findViewById<TextView>(R.id.tvTotalPoint)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_leaderboard, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val leaderboard = leaderboardList[position]
        holder.tvNickName.text = leaderboard.nickname
        holder.tvTotalPoint.text = leaderboard.totalPoint.toString()
    }

    override fun getItemCount(): Int {
        return leaderboardList.size
    }

}