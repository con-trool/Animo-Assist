package com.mobdeve.s13.grp50.mc02_mobdeve

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.grp50.mc02_mobdeve.R

class RatePopupActivity : AppCompatActivity() {

    private lateinit var star1: ImageView
    private lateinit var star2: ImageView
    private lateinit var star3: ImageView
    private lateinit var star4: ImageView
    private lateinit var star5: ImageView
    private lateinit var feedbackInput: EditText
    private lateinit var submitButton: Button
    private lateinit var rateTitle: TextView
    private var rating: Int = 0  // Stores the user's rating

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rate_popup)

        // Initialize views
        star1 = findViewById(R.id.star1)
        star2 = findViewById(R.id.star2)
        star3 = findViewById(R.id.star3)
        star4 = findViewById(R.id.star4)
        star5 = findViewById(R.id.star5)
        feedbackInput = findViewById(R.id.feedbackInput)
        submitButton = findViewById(R.id.submitRating)
        rateTitle = findViewById(R.id.rateTitle)

        // Set onClick listeners for the stars to update the rating
        star1.setOnClickListener { updateRating(1) }
        star2.setOnClickListener { updateRating(2) }
        star3.setOnClickListener { updateRating(3) }
        star4.setOnClickListener { updateRating(4) }
        star5.setOnClickListener { updateRating(5) }

        // Handle the Submit button click
        submitButton.setOnClickListener {
            submitRating()
        }
    }

    // Update the rating based on the star clicked
    private fun updateRating(starCount: Int) {
        rating = starCount

        // Update the star images based on the rating
        star1.setImageResource(if (starCount >= 1) R.drawable.ic_star_filled else R.drawable.ic_star_border)
        star2.setImageResource(if (starCount >= 2) R.drawable.ic_star_filled else R.drawable.ic_star_border)
        star3.setImageResource(if (starCount >= 3) R.drawable.ic_star_filled else R.drawable.ic_star_border)
        star4.setImageResource(if (starCount >= 4) R.drawable.ic_star_filled else R.drawable.ic_star_border)
        star5.setImageResource(if (starCount >= 5) R.drawable.ic_star_filled else R.drawable.ic_star_border)
    }

    // Submit the rating to Firestore
    // When submitting the rating in RatePopupActivity.kt
    private fun submitRating() {
        val user = auth.currentUser
        if (user != null) {
            // Get the feedback if available
            val feedback = feedbackInput.text.toString().takeIf { it.isNotEmpty() }

            // Create a new report record with the rating, feedback, and user info
            val report = hashMapOf(
                "rating" to if (rating > 0) rating else null,  // Store the rating as an integer (1-5)
                "feedback" to feedback,
                "user_id" to user.uid,
                "timestamp" to System.currentTimeMillis()
            )

            // Save the report to Firestore
            db.collection("Reports")
                .add(report)
                .addOnSuccessListener {
                    // Success, show a message to the user
                    showToast("Your rating has been submitted!")
                    finish()  // Close the rating popup after submission
                }
                .addOnFailureListener { e ->
                    // Error occurred, show an error message
                    showToast("Failed to submit your rating. Please try again later.")
                }
        } else {
            showToast("Please log in to submit your rating.")
        }
    }



    // Show a simple toast message
    private fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }
}
