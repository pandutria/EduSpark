package com.example.eduspark.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.eduspark.R
import com.example.eduspark.domain.DataParsing.DataParsingGame

class HomeScreen : AppCompatActivity() {
    lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        rv = findViewById(R.id.rv)

        DataParsingGame(this,rv).execute()
    }
}