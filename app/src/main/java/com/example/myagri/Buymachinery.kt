package com.example.myagri

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Buymachinery : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buymachinery)

        // Get references to the buttons and other views
        val buyButton = findViewById<Button>(R.id.feedbtn1)
        val addCommentButton = findViewById<Button>(R.id.feedbtn2) // Reference to the Add Comment button
        val machineryName = "John Deere Tractor" // The name from the TextView
        val machineryImage = R.drawable.tactor // The image resource

        // Handle Buy Button Click
        buyButton.setOnClickListener {
            val intent = Intent(this, StaticBuyPage::class.java)
            // Pass the image and name to the next activity
            intent.putExtra("image", machineryImage)
            intent.putExtra("name", machineryName)
            startActivity(intent)
        }

        // Handle Add Comment Button Click
        addCommentButton.setOnClickListener {
            val intent = Intent(this, Comment::class.java) // Start Comment activity
            startActivity(intent)
        }
    }
}
