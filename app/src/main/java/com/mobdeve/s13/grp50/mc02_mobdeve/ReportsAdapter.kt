package com.mobdeve.s13.grp50.mc02_mobdeve

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ReportsAdapter(private val reports: List<Report>, private val onItemClick: (Report) -> Unit) :
    RecyclerView.Adapter<ReportsAdapter.ReportViewHolder>() {

    class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val imgStatus: ImageView = itemView.findViewById(R.id.imgStatus)
        val txtStatus: TextView = itemView.findViewById(R.id.txtStatus)
        val txtDate: TextView = itemView.findViewById(R.id.txtDate)
        val imgRate: ImageView = itemView.findViewById(R.id.imgRate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.report_entry, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val report = reports[position]

        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(report.timestamp))

        holder.txtTitle.text = report.title
        holder.txtStatus.text = report.status
        holder.txtDate.text = formattedDate

        when (report.status) {
            "Resolved" -> holder.imgStatus.setImageResource(R.drawable.resolved_icon)
            "Pending" -> holder.imgStatus.setImageResource(R.drawable.pending_icon)
            "In-progress" -> holder.imgStatus.setImageResource(R.drawable.inprogress_icon)
            else -> holder.imgStatus.setImageResource(R.drawable.ic_star_filled)
        }

        // Show rate button only if status is resolved and no rating exists
        holder.imgRate.visibility = if (report.status == "Resolved" && (report.rating == null || report.rating == 0)) {
            View.VISIBLE
        } else {
            View.GONE
        }

        // Handle item click
        holder.itemView.setOnClickListener {
            onItemClick(report)
        }

        // Show rating popup when rate button is clicked
        holder.imgRate.setOnClickListener {
            showRatingPopup(holder.itemView.context, report.id)
        }
    }


    override fun getItemCount(): Int = reports.size

    // Function to show the custom rating popup dialog
    private fun showRatingPopup(context: Context, reportId: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.rate_popup, null)

        val star1 = dialogView.findViewById<ImageView>(R.id.star1)
        val star2 = dialogView.findViewById<ImageView>(R.id.star2)
        val star3 = dialogView.findViewById<ImageView>(R.id.star3)
        val star4 = dialogView.findViewById<ImageView>(R.id.star4)
        val star5 = dialogView.findViewById<ImageView>(R.id.star5)
        val feedbackInput = dialogView.findViewById<EditText>(R.id.feedbackInput)
        val submitButton = dialogView.findViewById<Button>(R.id.submitRating)

        val stars = listOf(star1, star2, star3, star4, star5)
        var selectedRating = 0

        stars.forEachIndexed { index, star ->
            star.setOnClickListener {
                selectedRating = index + 1
                updateStars(stars, selectedRating)
            }
        }

        val alertDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        submitButton.setOnClickListener {
            val feedback = feedbackInput.text.toString()
            saveRatingToFirebase(reportId, selectedRating, feedback, context)
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    // Helper function to update star images based on selection
    private fun updateStars(stars: List<ImageView>, rating: Int) {
        stars.forEachIndexed { index, star ->
            star.setImageResource(
                if (index < rating) R.drawable.ic_star_filled else R.drawable.ic_star_border
            )
        }
    }

    private fun saveRatingToFirebase(reportId: String, rating: Int, feedback: String, context: Context) {
        val reportRef = FirebaseFirestore.getInstance().collection("Reports").document(reportId)

        reportRef.update(
            mapOf(
                "rating" to rating,
                "feedback" to feedback
            )
        ).addOnSuccessListener {
            showToast(context, "Rating submitted successfully!")

            // Refresh the activity after rating submission
            if (context is SubmittedReportsActivity) {
                context.refreshReports()
            }
        }.addOnFailureListener {
            showToast(context, "Failed to submit rating.")
        }
    }


    // Helper function to show a toast message
    private fun showToast(context: Context, message: String) {
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
    }
}
