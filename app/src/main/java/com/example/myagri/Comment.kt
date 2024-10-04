package com.example.myagri

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import androidx.appcompat.app.AppCompatActivity

class Comment : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var commentEditText: EditText
    private lateinit var ratingBar: RatingBar
    private lateinit var addCommentButton: Button
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        nameEditText = findViewById(R.id.ComeName)
        commentEditText = findViewById(R.id.comcom)
        ratingBar = findViewById(R.id.ratingBar)
        addCommentButton = findViewById(R.id.combtn)

        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance()

        addCommentButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val comment = commentEditText.text.toString()
            val rating = ratingBar.rating

            if (name.isNotEmpty() && comment.isNotEmpty()) {
                // Save the comment and rating to Firestore
                val review = hashMapOf(
                    "name" to name,
                    "comment" to comment,
                    "rating" to rating
                )

                firestore.collection("reviews")
                    .add(review)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Review added successfully", Toast.LENGTH_SHORT).show()

                        // Navigate to AllReviews activity after saving the review
                        val intent = Intent(this, AllReviews::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear back stack
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to add review: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
