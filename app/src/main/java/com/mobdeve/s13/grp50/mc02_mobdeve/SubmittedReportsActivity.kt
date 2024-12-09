package com.mobdeve.s13.grp50.mc02_mobdeve

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.bumptech.glide.Glide

class SubmittedReportsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var reportsAdapter: ReportsAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menuIcon: ImageView
    private lateinit var firestore: FirebaseFirestore

    private var userEmail: String? = null // To hold the user's email

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submitted_reports)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val btnBack: Button = findViewById(R.id.btnBack)
        btnBack.setOnClickListener { super.onBackPressed() }

        drawerLayout = findViewById(R.id.drawer_layout)
        menuIcon = findViewById(R.id.menuIcon)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Retrieve the email from SharedPreferences
        userEmail = getSharedPreferences("UserDetails", Context.MODE_PRIVATE).getString("USER_EMAIL", null)

        if (userEmail == null) {
            Toast.makeText(this, "User email not available. Cannot fetch reports.", Toast.LENGTH_SHORT).show()
            Log.e("SubmittedReportsActivity", "User email is missing.")
            return
        }

        // Log the email for debugging
        Log.d("SubmittedReportsActivity", "Fetching reports for email: $userEmail")

        // Fetch reports from Firestore
        fetchReportsFromFirestore()

        menuIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupDrawerItemListeners()

        val margin = resources.getDimensionPixelSize(R.dimen.item_margin)
        recyclerView.addItemDecoration(MarginItemDecoration(margin))
    }

    private fun fetchReportsFromFirestore() {
        if (userEmail.isNullOrEmpty()) {
            Toast.makeText(this, "Email is missing. Cannot fetch reports.", Toast.LENGTH_SHORT).show()
            return
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val querySnapshot = firestore.collection("Reports")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .await()

                val reports = mutableListOf<Report>()
                for (document in querySnapshot.documents) {
                    val title = document.getString("reportType") ?: "Untitled Report"
                    val status = document.getString("status") ?: "Unknown"

                    val timestampString = document.getString("timestamp") ?: "N/A"
                    val timestamp = try {
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        val date = dateFormat.parse(timestampString)
                        date?.time ?: 0L
                    } catch (e: Exception) {
                        0L
                    }

                    val floorRoom = document.getString("floorRoom") ?: "N/A"
                    val location = document.getString("location") ?: "N/A"
                    val imageUrl = document.getString("imageUrl")
                    val description = document.getString("description") ?: "N/A"
                    val id = document.id

                    // Handle rating retrieval and conversion
                    val rate = when (val ratingValue = document.get("rating")) {
                        is Long -> ratingValue.toInt()
                        is String -> ratingValue.toIntOrNull() ?: 0
                        else -> 0
                    }

                    reports.add(Report(id, rate, description, title, status, timestamp, floorRoom, location, imageUrl))
                }

                withContext(Dispatchers.Main) {
                    if (reports.isEmpty()) {
                        Toast.makeText(this@SubmittedReportsActivity, "No reports found for this email.", Toast.LENGTH_SHORT).show()
                    } else {
                        updateRecyclerView(reports)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@SubmittedReportsActivity, "Failed to fetch reports: ${e.message}", Toast.LENGTH_SHORT).show()
                }
                Log.e("SubmittedReportsActivity", "Error fetching reports", e)
            }
        }
    }


    fun refreshReports() {
        fetchReportsFromFirestore()
    }

    private fun updateRecyclerView(reports: List<Report>) {
        reportsAdapter = ReportsAdapter(reports) { report ->
            showReportDetails(report)
        }
        recyclerView.adapter = reportsAdapter
    }

    private fun showReportDetails(report: Report) {
        // Format the timestamp to a more readable date/time format
        val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(report.timestamp))

        val builder = AlertDialog.Builder(this)
        builder.setTitle(report.title)
            .setMessage("""
            Status: ${report.status}
             Description:  ${report.description}
            Date & Time: $formattedDate
            Room: ${report.floorRoom}
            Location: ${report.location}    
        """.trimIndent())

        // If there is an image URL, load it using an image loading library like Glide or Picasso
        if (!report.imageUrl.isNullOrEmpty()) {
            val imageView = ImageView(this)
            Glide.with(this)
                .load(report.imageUrl)
                .placeholder(R.drawable.loading) // Optional: Add a placeholder image while loading
                .error(R.drawable.addlocation) // Optional: Add an error image if loading fails
                .into(imageView)

            builder.setView(imageView)  // Set the ImageView as the custom view in the dialog
        }

        builder.setPositiveButton("OK", null)
            .show()
    }



    private fun setupDrawerItemListeners() {
        val llSubmittedReports: LinearLayout = findViewById(R.id.llSubmittedReports)
        val llHome: LinearLayout = findViewById(R.id.llHome)
        val llFaq: LinearLayout = findViewById(R.id.llFaq)
        val llLogout: LinearLayout = findViewById(R.id.llLogout)

        llSubmittedReports.setOnClickListener {
            val intent = Intent(this, SubmittedReportsActivity::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        llHome.setOnClickListener {
            val intent = Intent(this, MainMenu::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        llFaq.setOnClickListener {
            val intent = Intent(this, FaqActivity::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        llLogout.setOnClickListener {
            val sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("USER_EMAIL") // Remove the USER_EMAIL key
            editor.apply() // Apply the changes

            // Navigate to the MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear the activity stack
            startActivity(intent)
            finish() // Close the current activity

            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
