package com.example.myagri

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Onboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard)

        val getStartedButton: Button = findViewById(R.id.getStBtn)
        getStartedButton.setOnClickListener {
            // Navigate to MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Call finish() to prevent returning to the Onboard activity
        }
    }
}
