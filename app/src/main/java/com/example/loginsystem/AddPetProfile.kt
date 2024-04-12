package com.example.loginsystem

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
class AddPetProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_pet_profiling)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val Buttonback: Button = findViewById(R.id.buttonBack)
        // Handle back button click event
        Buttonback.setOnClickListener {
            onBackPressed() // Go back to the previous activity
        }
    }

    // Handle back button click event
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Check if back button is pressed
        if (item.itemId == android.R.id.home) {
            onBackPressed() // Go back to the previous activity
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
