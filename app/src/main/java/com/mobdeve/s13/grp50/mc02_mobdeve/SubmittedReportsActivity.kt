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
                // Query Firestore for reports where the email matches
                val querySnapshot = firestore.collection("Reports")
                    .whereEqualTo("email", userEmail)
                    .get()
                    .await()

                val reports = mutableListOf<Report>()
                for (document in querySnapshot.documents) {
                    // Extract the required fields
                    val title = document.getString("reportType") ?: "Untitled Report"
                    val status = document.getString("status") ?: "Unknown"

                    // Add the report to the list (skip the timestamp)
                    reports.add(Report(title, status, "N/A"))
                }

                withContext(Dispatchers.Main) {
                    // Update the RecyclerView with the retrieved reports
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

    private fun updateRecyclerView(reports: List<Report>) {
        reportsAdapter = ReportsAdapter(reports) { report ->
            showReportDetails(report)
        }
        recyclerView.adapter = reportsAdapter
    }

    private fun showReportDetails(report: Report) {
        AlertDialog.Builder(this)
            .setTitle(report.title)
            .setMessage("Status: ${report.status}")
            .setPositiveButton("OK", null)
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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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
