package com.example.loginsystem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TestIntent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test) // Make sure test.xml exists and is correctly defined
    }
}