package com.example.loginsystem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance()

        // Find the button by its ID
        val signOutButton: Button = view.findViewById(R.id.buttonSignOut)

        // Set click listener for the sign-out button
        signOutButton.setOnClickListener {
            // Call signOut method to log out the user
            firebaseAuth.signOut()

            // After signing out, navigate back to LoginActivity
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)

            // Finish the current activity to prevent going back to ProfileFragment
            activity?.finish()
        }

        return view
    }
}
