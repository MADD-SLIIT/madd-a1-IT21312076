package com.example.myagri

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Profile : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()

    // UI elements
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView
    private lateinit var profilePhone: TextView
    private lateinit var profilePassword: TextView
    private lateinit var editProfileButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make the window edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(R.layout.activity_profile)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize UI elements
        profileName = findViewById(R.id.profileName)
        profileEmail = findViewById(R.id.profileEmail)
        profilePhone = findViewById(R.id.profilePhone)
        profilePassword = findViewById(R.id.profilePassword)
        editProfileButton = findViewById(R.id.editProfileButton)

        // Handle window insets
        val rootView = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Update padding to accommodate system bars
            v.updatePadding(top = systemBars.top, bottom = systemBars.bottom)
            insets
        }

        // Retrieve user profile data
        retrieveUserProfile()

        // Set up edit profile button listener
        editProfileButton.setOnClickListener {
            // Add code to handle profile editing
            // You could start an EditProfile activity here
        }
    }

    private fun retrieveUserProfile() {
        val userEmail = auth.currentUser?.email

        if (userEmail != null) {
            firestore.collection("users").document(userEmail).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Retrieve data from Firestore
                        val name = document.getString("name") ?: "No Name"
                        val email = document.getString("email") ?: "No Email"
                        val phone = document.getString("phone") ?: "No Phone"
                        // You might not want to display the actual password, consider using a placeholder
                        val password = document.getString("password") ?: "Not Available"

                        // Set the data to the TextViews
                        profileName.text = name
                        profileEmail.text = email
                        profilePhone.text = phone
                        profilePassword.text = "Password: $password" // Consider how to handle passwords securely
                    } else {
                        Toast.makeText(this, "No user data found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error fetching data: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }
}
