package com.example.myagri

import android.content.Intent // Add this import
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    // Firebase Auth instance
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Apply window insets for padding adjustments
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        // Initialize views
        val emailEditText = findViewById<EditText>(R.id.edtEmail)
        val passwordEditText = findViewById<EditText>(R.id.edtPassword)
        val loginButton = findViewById<Button>(R.id.btnlog3)
        val createAccountButton = findViewById<Button>(R.id.btnCreateAccount)
        val rememberMeCheckBox = findViewById<CheckBox>(R.id.checkBox)

        // Set up login button click listener
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

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

            loginUser(email, password)
        }

        // Set up create account button click listener
        createAccountButton.setOnClickListener {
            val intent = Intent(this, SignUp::class.java) // Redirect to SignUp activity
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login success, show a success message
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                } else {
                    // If login fails, show error message
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
