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

        holder.txtTitle.text = report.title
        holder.txtStatus.text = report.status
        holder.txtDate.text = report.date
        when (report.status) {
            "Resolved" -> {
                holder.imgStatus.setImageResource(R.drawable.resolved_icon) // Replace with your icon resource
            }
            "Pending" -> {
                holder.imgStatus.setImageResource(R.drawable.pending_icon) // Replace with your icon resource
            }
            "In-progress" -> {
                holder.imgStatus.setImageResource(R.drawable.inprogress_icon) // Replace with your icon resource
            }
            else -> {
                holder.imgStatus.setImageResource(R.drawable.ic_star_filled) // Default icon if status is unknown
            }
        }
        // Show rate button only if status is resolved
        holder.imgRate.visibility = if (report.status == "Resolved") View.VISIBLE else View.GONE

        // Handle item click
        holder.itemView.setOnClickListener {
            onItemClick(report)
        }

        // Show rating popup when rate button is clicked
        holder.imgRate.setOnClickListener {
            showRatingPopup(holder.itemView.context)
        }
    }

    // This method returns the total number of items in the dataset
    override fun getItemCount(): Int {
        return reports.size
    }

    // Function to show the custom rating popup dialog
    private fun showRatingPopup(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.rate_popup, null)

        val star1 = dialogView.findViewById<ImageView>(R.id.star1)
        val star2 = dialogView.findViewById<ImageView>(R.id.star2)
        val star3 = dialogView.findViewById<ImageView>(R.id.star3)
        val star4 = dialogView.findViewById<ImageView>(R.id.star4)
        val star5 = dialogView.findViewById<ImageView>(R.id.star5)
        val feedbackInput = dialogView.findViewById<EditText>(R.id.feedbackInput)
        val submitButton = dialogView.findViewById<Button>(R.id.submitRating)

        // Handle star rating click
        val stars = listOf(star1, star2, star3, star4, star5)
        var selectedRating = 0

        stars.forEachIndexed { index, star ->
            star.setOnClickListener {
                selectedRating = index + 1
                updateStars(stars, selectedRating)
            }
        }

        // Create and show the dialog
        val alertDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        submitButton.setOnClickListener {
            // Handle submit action here (e.g., save feedback and rating)
            val feedback = feedbackInput.text.toString()
            // Save selectedRating and feedback to server or database

            alertDialog.dismiss() // Close the dialog
        }

        alertDialog.show()
    }

    // Helper function to update star images based on selection
    private fun updateStars(stars: List<ImageView>, rating: Int) {
        stars.forEachIndexed { index, star ->
            if (index < rating) {
                star.setImageResource(R.drawable.ic_star_filled) // Filled star for selected
            } else {
                star.setImageResource(R.drawable.ic_star_border) // Empty star for unselected
            }
        }
    }
}