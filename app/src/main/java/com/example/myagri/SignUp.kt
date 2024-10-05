package com.example.myagri

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUp : AppCompatActivity() {

    // Firebase Auth instance
    private lateinit var auth: FirebaseAuth
    // Firebase Firestore reference
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views
        val emailEditText = findViewById<EditText>(R.id.edtEmail)
        val passwordEditText = findViewById<EditText>(R.id.edtPassword)
        val signUpButton = findViewById<Button>(R.id.btnlog3)
        val agreeCheckBox = findViewById<CheckBox>(R.id.checkBox)

        // Set up sign-up button click listener
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validate inputs
            if (email.isEmpty()) {
                emailEditText.error = "Please enter your email"
                emailEditText.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordEditText.error = "Please enter your password"
                passwordEditText.requestFocus()
                return@setOnClickListener
            }

            if (!agreeCheckBox.isChecked) {
                Toast.makeText(this, "Please agree to terms and conditions", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Call the sign-up method
            signUpUser(email, password)
        }
    }

    private fun signUpUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-up success
                    Toast.makeText(this, "Sign Up successful", Toast.LENGTH_SHORT).show()

                    // Save user details to Firestore
                    saveProfileDetails(email)

                    // Navigate to the login activity
                    val intent = Intent(this, MainActivity::class.java)  // Assuming your login activity is called 'MainActivity'
                    startActivity(intent)
                    finish()  // Close the SignUp activity so it doesn't stay in the back stack
                } else {
                    // If sign up fails, display a message to the user.
                    Toast.makeText(this, "Sign Up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()

                    // Log the error for debugging
                    task.exception?.printStackTrace()  // This will log the full exception details to the console
                }
            }
    }

    private fun saveProfileDetails(email: String) {
        // Create a user map to store in Firestore
        val user = hashMapOf(
            "email" to email
        )

        // Write data to Firestore
        firestore.collection("users").document(email).set(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to update profile: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
