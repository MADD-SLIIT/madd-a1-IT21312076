package com.example.myagri

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AllReviews : AppCompatActivity() {

    private lateinit var reviewRecyclerView: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private val reviewList = mutableListOf<Review>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_reviews)

        reviewRecyclerView = findViewById(R.id.reviewRecyclerView)
        reviewRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter
        reviewAdapter = ReviewAdapter(reviewList)
        reviewRecyclerView.adapter = reviewAdapter

        // Get the passed data
        val name = intent.getStringExtra("name")
        val comment = intent.getStringExtra("comment")
        val rating = intent.getFloatExtra("rating", 0f)

        // Add the new review to the list if data is not null
        if (!name.isNullOrEmpty() && !comment.isNullOrEmpty()) {
            reviewList.add(Review(name, comment, rating))
        }

        // Update the RecyclerView and handle empty reviews scenario
        if (reviewList.isEmpty()) {
            findViewById<TextView>(R.id.noReviewsTextView).visibility = View.VISIBLE
            reviewRecyclerView.visibility = View.GONE // Hide RecyclerView
        } else {
            findViewById<TextView>(R.id.noReviewsTextView).visibility = View.GONE
            reviewRecyclerView.visibility = View.VISIBLE // Show RecyclerView
            reviewAdapter.notifyDataSetChanged() // Notify adapter to refresh
        }

        // Find the back button and set click listener
        val backButton: Button = findViewById(R.id.rebtn1)
        backButton.setOnClickListener {
            // Navigate back to BuyMachinery activity
            val intent = Intent(this, Buymachinery::class.java)
            startActivity(intent)
        }
    }
}
