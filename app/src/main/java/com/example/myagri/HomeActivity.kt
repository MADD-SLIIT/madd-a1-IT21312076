package com.example.myagri

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.myagri.R
import com.example.myagri.Task

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Get the reference to the cardView2
        val cardView2 = findViewById<CardView>(R.id.cardView2)

        // Set an OnClickListener to cardView2 to redirect to the Task page
        cardView2.setOnClickListener {
            // Navigate to TaskActivity when cardView2 is clicked
            val intent = Intent(this, Task::class.java) // Assuming TaskActivity is the Task page
            startActivity(intent)
        }

        // Get the reference to the cardView2 (Buy Machinery Card)
        val cardView3 = findViewById<CardView>(R.id.cardView3)

        // Set an OnClickListener to cardView2 to redirect to the Buy Machinery page
        cardView3.setOnClickListener {
            // Navigate to BuyMachineryActivity when cardView2 is clicked
            val intent = Intent(this, Buymachinery::class.java)
            startActivity(intent)
        }

        // Get the reference to imageView10 and navigate to Profile page
        val imageView10 = findViewById<ImageView>(R.id.imageView10)
        imageView10.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }
    }
}
