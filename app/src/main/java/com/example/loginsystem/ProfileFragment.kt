package com.example.loginsystem

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProfileFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var imageRef: StorageReference
    private lateinit var propicImg: ShapeableImageView
    private lateinit var editProfileBtn: MaterialButton
    private lateinit var signOutBtn: MaterialButton
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize Firebase Storage reference
        imageRef = FirebaseStorage.getInstance().reference.child("profile_images")

        // Find views by their IDs
        propicImg = view.findViewById(R.id.propcimg)
        editProfileBtn = view.findViewById(R.id.editProfile)
        signOutBtn = view.findViewById(R.id.buttonSignOut)

        // Load user's profile image
        loadProfileImage()

        // Set click listener for the "Edit Profile" button
        editProfileBtn.setOnClickListener {
            chooseImage()
        }

        // Set click listener for the "Sign Out" button
        signOutBtn.setOnClickListener {
            signOut()
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

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, filePath)
                propicImg.setImageBitmap(bitmap)
                uploadImage()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        if (filePath != null) {
            val userId = firebaseAuth.currentUser?.uid ?: return
            val ref = imageRef.child("$userId.jpg")
            ref.putFile(filePath!!)
                .addOnSuccessListener {
                    // Image uploaded successfully
                }
                .addOnFailureListener { e ->
                    // Handle any errors that may occur during upload
                    e.printStackTrace()
                }
        }
    }

    private fun signOut() {
        firebaseAuth.signOut()
        // After signing out, navigate back to the login screen
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }
}
