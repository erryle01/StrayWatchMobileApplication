package com.example.loginsystem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Find the button by its ID
        val button: Button = view.findViewById(R.id.buttonProfiling)

        // Set click listener for the button
        button.setOnClickListener {
            // Create an Intent to navigate to the other page (activity or fragment)
            val intent = Intent(requireContext(), AddPetProfile::class.java)
            startActivity(intent)
        }

        return view
    }
}
