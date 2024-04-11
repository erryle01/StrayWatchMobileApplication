package com.example.loginsystem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private var previousItemId: Int = R.id.navigation_item1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hello)

        // Initialize bottom navigation view
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set default fragment
        replaceFragment(HomeFragment())

        // Set navigation item click listener
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            // Update previous item ID
            previousItemId = bottomNavigationView.selectedItemId

            when (menuItem.itemId) {
                R.id.navigation_item1 -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.navigation_item2 -> {
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.navigation_item3 -> {
                    replaceFragment(MessageFragment())
                    true
                }
                R.id.navigation_item4 -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        // Restore the previously selected item when back button is pressed
        bottomNavigationView.selectedItemId = previousItemId
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
