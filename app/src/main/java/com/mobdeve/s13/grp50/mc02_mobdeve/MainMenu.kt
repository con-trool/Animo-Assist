package com.mobdeve.s13.grp50.mc02_mobdeve

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainMenu : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menuIcon: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)


        // Initialize views
        drawerLayout = findViewById(R.id.drawer_layout)
        menuIcon = findViewById(R.id.menuIcon)

        // Handle the menu icon click to open/close the drawer
        menuIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        // Handle window insets for immersive mode
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up report buttons click listeners
        setupReportButtons()

        // Set up drawer item click listeners
        setupDrawerItemListeners()
    }

    private fun setupReportButtons() {
        val genConBtn: ImageView = findViewById(R.id.genconbtn)
        val irBtn: ImageView = findViewById(R.id.irbtn)
        val inqBtn: ImageView = findViewById(R.id.inqbtn)
        val rrBtn: ImageView = findViewById(R.id.rrbtn)

        genConBtn.setOnClickListener {
            openReportPage("General Concerns")
        }

        irBtn.setOnClickListener {
            openReportPage("Incident Report")
        }

        inqBtn.setOnClickListener {
            openReportPage("Inquiry")
        }

        rrBtn.setOnClickListener {
            openReportPage("Repair Report")
        }
    }

    private fun openReportPage(reportType: String) {
        val email = getSharedPreferences("UserDetails", Context.MODE_PRIVATE).getString("USER_EMAIL", null)

        if (email != null) {
            val intent = Intent(this, ReportPage::class.java)
            intent.putExtra("REPORT_TYPE", reportType) // Pass the report type
            intent.putExtra("USER_EMAIL", email) // Pass the email
            startActivity(intent)
        } else {
            Toast.makeText(this, "User email is missing. Cannot proceed to report page.", Toast.LENGTH_SHORT).show()
        }

        Log.d("MainMenu", "Navigating to ReportPage with email: $email and reportType: $reportType")
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
