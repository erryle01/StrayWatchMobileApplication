package com.example.loginsystem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Find the button by its ID
        val otherActivityButton: Button = view.findViewById(R.id.buttonTest)

        // Set click listener for the button
        otherActivityButton.setOnClickListener {
            // Create an Intent to navigate to the other activity
            val intent = Intent(activity, TestIntent::class.java)
            // Start the activity
            startActivity(intent)
        }

        return view
    }
}

