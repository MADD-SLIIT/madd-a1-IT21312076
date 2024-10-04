package com.example.myagri

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class StaticBuyPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ensure this is the correct layout file
        setContentView(R.layout.activity_static_buy_page)

        // Retrieve the image resource and name passed from the previous activity
        val imageResource = intent.getIntExtra("image", 0)
        val name = intent.getStringExtra("name")

        // Find views
        val imageView = findViewById<ImageView>(R.id.static_image)
        val textView = findViewById<TextView>(R.id.static_name)
        val backButton = findViewById<Button>(R.id.Backbtn1) // Get the back button reference

        // Set the image and name
        imageView.setImageResource(imageResource)
        textView.text = name

        // Handle Back Button Click
        backButton.setOnClickListener {
            finish() // This will close the current activity and return to the previous one
        }
    }
}
