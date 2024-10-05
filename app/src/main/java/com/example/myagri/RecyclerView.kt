package com.example.myagri


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter(private val reviewList: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.reviewNameTextView)
        val commentTextView: TextView = itemView.findViewById(R.id.reviewCommentTextView)
        val ratingBar: RatingBar = itemView.findViewById(R.id.reviewRatingBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]
        holder.nameTextView.text = review.name
        holder.commentTextView.text = review.comment
        holder.ratingBar.rating = review.rating
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }
}

