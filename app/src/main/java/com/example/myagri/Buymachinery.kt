package com.example.myagri

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class Buymachinery : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buymachinery)

        // Apply edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup button for navigating to a static page (Buy button)
        val buyButton: Button = findViewById(R.id.feedbtn1)
        buyButton.setOnClickListener {
            val intent = Intent(this, StaticBuyPage::class.java) // StaticBuyPage will be your new page
            startActivity(intent)
        }

        // Setup button for navigating to comment page (Add Comment button)
        val addCommentButton: Button = findViewById(R.id.feedbtn2)
        addCommentButton.setOnClickListener {
            val intent = Intent(this, Comment::class.java)
            startActivity(intent)
        }
    }
}
