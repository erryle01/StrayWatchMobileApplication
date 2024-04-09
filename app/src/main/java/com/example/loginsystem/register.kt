package com.example.loginsystem

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.loginsystem.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : ComponentActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.alreadyHaveAnAccount.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener {
            val name = binding.registerName.text.toString().trim()
            val email = binding.registerEmail.text.toString().trim()
            val pass = binding.registerPassword.text.toString().trim()
            val confirmPass = binding.confirmRegisterPassword.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = firebaseAuth.currentUser
                                // Update user profile
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build()
                                user?.updateProfile(profileUpdates)
                                    ?.addOnCompleteListener { profileTask ->
                                        if (profileTask.isSuccessful) {
                                            // Profile updated successfully
                                            val userData = hashMapOf(
                                                "name" to name,
                                                "email" to email
                                            )
                                            // Save user data to Firestore
                                            firestore.collection("users").document(user!!.uid)
                                                .set(userData)
                                                .addOnSuccessListener {
                                                    // User data saved successfully
                                                    val intent = Intent(this, LoginActivity::class.java)
                                                    startActivity(intent)
                                                }
                                                .addOnFailureListener { e ->
                                                    // Failed to save user data
                                                    Toast.makeText(this, "Failed to save user data: ${e.message}", Toast.LENGTH_SHORT).show()
                                                }
                                        } else {
                                            // Profile update failed
                                            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            } else {
                                // Show the error message
                                Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty fields are not allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
