package com.example.loginsystem


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var userNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hello)

        // Initialize views
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        userNameTextView = findViewById(R.id.user_name)

        // Set up bottom navigation view
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            // Handle bottom navigation item selection
            when (menuItem.itemId) {
                // Handle each menu item click
                R.id.navigation_item1 -> {
                    // Handle item 1 click
                    true
                }
                R.id.navigation_item2 -> {
                    // Handle item 2 click
                    true
                }
                R.id.navigation_item3 -> {
                    // Handle item 3 click
                    true
                }
                R.id.navigation_item4 -> {
                    // Handle item 4 click
                    true
                }
                else -> false
            }
        }

        // Set user's name
        userNameTextView.text = "John Doe" // Set the actual user's name here
    }
}