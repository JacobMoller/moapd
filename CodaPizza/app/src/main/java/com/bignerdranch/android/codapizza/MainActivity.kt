package com.bignerdranch.android.codapizza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.bignerdranch.android.codapizza.ui.PizzaBuilderScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzaBuilderScreen()
        }
    }
}