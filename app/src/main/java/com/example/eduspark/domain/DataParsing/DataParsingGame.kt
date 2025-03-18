package com.example.eduspark.domain.DataParsing

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eduspark.adapter.GameAdapter
import com.example.eduspark.domain.HttpHandler
import com.example.eduspark.model.Game
import org.json.JSONArray

class DataParsingGame(private val context: Context,  val rv: RecyclerView) : AsyncTask<Void, Void, Void>() {

    var gameList: MutableList<Game> = arrayListOf()
    override fun doInBackground(vararg p0: Void?): Void? {
        try {
            var jsonToUrl = HttpHandler().getRequest("games")
            var jsonToArray = JSONArray(jsonToUrl)

            for (i in 0 until jsonToArray.length()) {
                var game = jsonToArray.getJSONObject(i)

                var id = game.getInt("id")
                var name = game.getString("name")
                var category = game.getString("category")
                var player = game.getInt("totalPlayer")

                gameList.add(Game(id, name,category,player))
        }

        } catch (e: Exception) {
            Log.d("HttpHandler", "Eror ${e.message}")
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        if (result.toString().isNotEmpty()) {
            rv.adapter = GameAdapter(gameList)
            rv.layoutManager = LinearLayoutManager(context)
        }
    }
}