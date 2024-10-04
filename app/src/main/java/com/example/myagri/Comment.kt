package com.example.myagri

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Comment : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var commentEditText: EditText
    private lateinit var ratingBar: RatingBar
    private lateinit var addCommentButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        nameEditText = findViewById(R.id.ComeName) // Ensure this ID is correct
        commentEditText = findViewById(R.id.comcom) // Ensure this ID is correct
        ratingBar = findViewById(R.id.ratingBar) // Ensure this ID is correct
        addCommentButton = findViewById(R.id.combtn) // Ensure this ID is correct

        addCommentButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val comment = commentEditText.text.toString()
            val rating = ratingBar.rating

            if (name.isNotEmpty() && comment.isNotEmpty()) {
                val intent = Intent(this, AllReviews::class.java)
                intent.putExtra("name", name)
                intent.putExtra("comment", comment)
                intent.putExtra("rating", rating)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
