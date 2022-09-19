package com.example.peak.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.peak.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, RectangleFragment())
            .commitNow()
    }
}