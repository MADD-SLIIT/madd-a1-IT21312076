package com.example.myagri

import Review
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class AllReviews : AppCompatActivity() {

    private lateinit var reviewRecyclerView: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private val reviewList = mutableListOf<Review>()
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_reviews)

        reviewRecyclerView = findViewById(R.id.reviewRecyclerView)
        reviewRecyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize the adapter
        reviewAdapter = ReviewAdapter(reviewList)
        reviewRecyclerView.adapter = reviewAdapter

        // Fetch reviews from Firestore
        fetchReviews()
    }

    private fun fetchReviews() {
        firestore.collection("reviews").get()
            .addOnSuccessListener { documents ->
                reviewList.clear() // Clear the list before adding new data
                for (document in documents) {
                    val name = document.getString("name")
                    val comment = document.getString("comment")
                    val rating = document.getDouble("rating")?.toFloat() ?: 0f

                    // Add each review to the list
                    reviewList.add(Review(name ?: "Unknown", comment ?: "No comment", rating))
                }

                // Update RecyclerView or handle empty reviews
                if (reviewList.isEmpty()) {
                    findViewById<TextView>(R.id.noReviewsTextView).visibility = View.VISIBLE
                    reviewRecyclerView.visibility = View.GONE // Hide RecyclerView
                } else {
                    findViewById<TextView>(R.id.noReviewsTextView).visibility = View.GONE
                    reviewRecyclerView.visibility = View.VISIBLE // Show RecyclerView
                    reviewAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { e ->
                // Handle failure to fetch data
                e.printStackTrace()
            }
    }
}
