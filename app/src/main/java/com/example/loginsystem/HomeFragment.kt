package com.example.loginsystem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class HomeFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var imageRef: StorageReference
    private lateinit var propicImg: ShapeableImageView
    private lateinit var usernameTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize views
        propicImg = view.findViewById(R.id.propcimg)
        usernameTextView = view.findViewById(R.id.username)

        // Initialize Firebase Storage reference
        imageRef = FirebaseStorage.getInstance().reference.child("profile_images")

        // Load profile image
        loadProfileImage()

        // Load username
        loadUserName()

        // Find the buttons by their IDs
        val signOutButton: MaterialButton = view.findViewById(R.id.buttonProfiling)
        val vaccineButton: MaterialButton = view.findViewById(R.id.buttonVaccine)
        val newsButton: MaterialButton = view.findViewById(R.id.buttonNews)
        val feedbackButton: MaterialButton = view.findViewById(R.id.buttonFeedback)

        // Set click listener for the sign-out button
        signOutButton.setOnClickListener {
            // Call signOut method to log out the user

            // After signing out, navigate back to LoginActivity
            val intent = Intent(activity,  testGraph::class.java)
            startActivity(intent)

            // Finish the current activity to prevent going back to ProfileFragment

        }

        // Vaccine button listener
        vaccineButton.setOnClickListener {
            // Create an intent to navigate to the VaccineSchedule activity
            val intent = Intent(activity, VaccineSchedule::class.java)
            startActivity(intent)
        }

        // News button listener
        newsButton.setOnClickListener {
            // Create an intent to navigate to the News activity
            val intent = Intent(activity, News::class.java)
            startActivity(intent)
        }

        // Feedback button listener
        feedbackButton.setOnClickListener {
            // Create an intent to navigate to the Test01 activity
            val intent = Intent(activity, Test01::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun loadProfileImage() {
        // Get the current user's ID
        val userId = firebaseAuth.currentUser?.uid ?: return

        // Construct a reference to the user's profile image
        val profileImageRef = imageRef.child("$userId.jpg")

        // Load the profile image into the ImageView using Glide
        profileImageRef.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(requireContext())
                .load(uri)
                .into(propicImg)
        }.addOnFailureListener { exception ->
            // Handle any errors that may occur while retrieving the image URL
            exception.printStackTrace()
        }
    }

    private fun loadUserName() {
        // Get the current user's display name
        val user = firebaseAuth.currentUser
        val userName = user?.displayName ?: "Unknown"

        // Set the user's name to the usernameTextView
        usernameTextView.text = userName
    }
}
