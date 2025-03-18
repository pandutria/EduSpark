package com.example.eduspark.domain.DataParsing

import android.content.Context
import android.os.AsyncTask
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eduspark.adapter.LeaderboardAdapter
import com.example.eduspark.domain.HttpHandler
import com.example.eduspark.model.Leaderboard
import org.json.JSONArray

class DataParsingLeaderboard(private var context: Context, private val rv: RecyclerView, private val gameId: Int) : AsyncTask<Void, String, Void>() {
    var leaderboardList: MutableList<Leaderboard> = arrayListOf()
    override fun doInBackground(vararg p0: Void?): Void? {
        var jsonToUrl = HttpHandler().getRequest("leaderboards/$gameId")
        var jsonToArray = JSONArray(jsonToUrl)

        for (i in 0 until jsonToArray.length()) {
            var leaderboard = jsonToArray.getJSONObject(i)
            var nickname = leaderboard.getString("nickname")
            var totalPoint = leaderboard.getInt("totalPoint")

            leaderboardList.add(Leaderboard(id = 0, nickname = nickname, totalPoint = totalPoint))
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        rv.adapter = LeaderboardAdapter(leaderboardList)
        rv.layoutManager = LinearLayoutManager(context)
    }
}