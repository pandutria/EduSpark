package com.example.eduspark.ui

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.eduspark.R
import com.example.eduspark.domain.HttpHandler
import com.example.eduspark.local.support
import org.json.JSONArray
import org.json.JSONObject

class GameScreen : AppCompatActivity() {
    var gameId = 0
    var wordsIndex = 0
    var lastWordsId = 0

    var word = ""
    var point = 0
    var totalPoint = 0

    lateinit var imgImage: ImageView
    lateinit var tvWord: TextView
    lateinit var etWord: EditText
    lateinit var btnPrev: Button
    lateinit var btnNext: Button
    lateinit var btnFinish: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        imgImage = findViewById(R.id.imgImage)
        tvWord = findViewById(R.id.tvWord)
        etWord = findViewById(R.id.etWord)
        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)
        btnFinish = findViewById(R.id.btnFinish)

        btnPrev.backgroundTintList = ContextCompat.getColorStateList(this, R.color.gray)
        btnNext.visibility = View.VISIBLE
        btnFinish.visibility = View.GONE

        btnNext.setOnClickListener {
            if (etWord.text.toString() == "") {
                Toast.makeText(this, "Field must be filled", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            wordsIndex++
            btnPrev.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)
            gameAsycnTask(this, gameId).execute()
            countTotalPoint()
            if (wordsIndex == lastWordsId) {
                btnNext.visibility = View.GONE
                btnFinish.visibility = View.VISIBLE
            }
        }

        btnPrev.setOnClickListener {
            wordsIndex--
            btnNext.backgroundTintList = ContextCompat.getColorStateList(this, R.color.blue)
            gameAsycnTask(this, gameId).execute()
            btnNext.visibility = View.VISIBLE
            btnFinish.visibility = View.GONE
            if (wordsIndex == 0) {
                btnPrev.backgroundTintList = ContextCompat.getColorStateList(this, R.color.gray)
            }
        }

        gameId = intent.getIntExtra("id", 0)

        gameAsycnTask(this, gameId).execute()

        btnFinish.setOnClickListener {
            if (etWord.text.toString() == "") {
                Toast.makeText(this, "Field must be filled", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val i = Intent(this, ScoreScreen::class.java)
            i.putExtra("gameId", gameId)
            i.putExtra("totalPoint", totalPoint)
            startActivity(i)
        }

    }

    fun countTotalPoint() {
        if (etWord.text.toString() == word) {
            totalPoint += point
        }
    }

    private class gameAsycnTask(var activity: GameScreen, private val gameId: Int) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg p0: String?): String {
            return HttpHandler().getRequest("words/$gameId")
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result.toString().isNotEmpty()) {
                var jsonToArray = JSONArray(result)
                activity.lastWordsId = jsonToArray.length() - 1
                var words = jsonToArray.getJSONObject(activity.wordsIndex)

                var word = words.getString("word")
                var randomWord = word.toList().shuffled().joinToString(" ")

                var image = words.getString("image")
                var point = words.getInt("point")

                activity.word = word
                activity.point = point

                activity.runOnUiThread {
                    activity.tvWord.text = randomWord
                    support.showImage(activity.imgImage).execute(support.urlImage + image)
                }
            }
        }
    }
}