package com.example.myagri

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AllReviews : AppCompatActivity() {

    private lateinit var reviewTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_reviews)

        reviewTextView = findViewById(R.id.reviewTextView)

        // Get the passed data
        val name = intent.getStringExtra("name")
        val comment = intent.getStringExtra("comment")
        val rating = intent.getFloatExtra("rating", 0f)

        // Display the review
        val reviewText = "Name: $name\nComment: $comment\nRating: $rating"
        reviewTextView.text = reviewText
    }
}
