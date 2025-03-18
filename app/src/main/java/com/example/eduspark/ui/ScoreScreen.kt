package com.example.eduspark.ui

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.eduspark.R
import com.example.eduspark.domain.DataParsing.DataParsingLeaderboard
import com.example.eduspark.domain.HttpHandler
import org.json.JSONObject

class ScoreScreen : AppCompatActivity() {
    lateinit var rv: RecyclerView
    lateinit var tvTotalPoint: TextView
    lateinit var etNickname: EditText
    lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_screen)

        var gameId = intent.getIntExtra("gameId", 0)
        var totalPoint = intent.getIntExtra("totalPoint", 0)

        rv = findViewById(R.id.rv)
        tvTotalPoint = findViewById(R.id.tvTotalPoint)
        etNickname = findViewById(R.id.etNickName)
        btnSubmit = findViewById(R.id.btnSubmit)

        tvTotalPoint.text = totalPoint.toString()
        DataParsingLeaderboard(this,rv, gameId).execute()

        btnSubmit.setOnClickListener {
            var data = leaderboardJson(etNickname.text.toString(), gameId, totalPoint)
//            sendData(this).execute("leaderboards", data)
            sendData(this, data).execute()
            DataParsingLeaderboard(this,rv, gameId).execute()

        }

        findViewById<ImageView>(R.id.home).setOnClickListener {
            finish()
        }

    }

    private fun leaderboardJson(nickname: String, gameID: Int, totalPoint: Int) : String {
        var json = JSONObject()
        json.put("nickname", nickname)
        json.put("totalPoint", totalPoint)
        json.put("gameID", gameID)


        return json.toString()

    }

    private class sendData(private val context: Context, private var data: String) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String {
            return HttpHandler().postRequest("leaderboards", data)
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result.toString().isNotEmpty()) {
                Toast.makeText(context, "Behrasil", Toast.LENGTH_SHORT).show()

            }

        }
    }

}