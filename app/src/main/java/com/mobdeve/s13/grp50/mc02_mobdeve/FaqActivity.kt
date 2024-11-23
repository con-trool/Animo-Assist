package com.mobdeve.s13.grp50.mc02_mobdeve

import FaqItem
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FaqActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var menuIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        // Find the RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val btnBack: Button = findViewById(R.id.btnBack)

        btnBack.setOnClickListener{
            super.onBackPressed()
        }

        drawerLayout = findViewById(R.id.drawer_layout)
        menuIcon = findViewById(R.id.menuIcon)

        // Create some sample data
        val faqList = listOf(
            FaqItem("What is AnimoAssist?", "AnimoAssist is a mobile app designed for the DLSU community to streamline IT-related support."),
            FaqItem("Who can use AnimoAssist?", "The app is exclusively for DLSU students, faculty, and staff with valid DLSU email accounts."),
            FaqItem("How do I register for AnimoAssist?", "You can register by providing your full name, ID number, course/program, DLSU email, and creating a password through the app’s registration page."),
            FaqItem("Is my information secure on AnimoAssist?", "Yes, AnimoAssist uses Google Identity Services (OAuth 2.0) for secure login and ensures data protection."),
            FaqItem("Do I need to pay to use AnimoAssist?", "No, AnimoAssist is free to use for all members of the DLSU community."),
            FaqItem("Can I use AnimoAssist outside the DLSU campus?", "Yes, as long as you have an internet connection, you can submit and manage reports from anywhere."),
            FaqItem("Can I change the email address associated with my account?", "No, your account is tied to your DLSU email. If you encounter issues, contact IT Services for assistance."),
            FaqItem("Can I delete my account?", "Yes, you can request account deletion through the settings page. Note that this will remove all your submitted reports and associated data."),
            FaqItem("What should I do if I suspect unauthorized access to my account?", "Immediately change your password through the app and report the issue to IT Services for investigation."),
            FaqItem("What types of issues can I report?", "You can report general concerns, accidents, incidents, inquiries, and repair requests related to IT services."),
            FaqItem("How do I attach images to my report?", "When filing a report, use the camera feature to capture or annotate images of your issue before submitting it."),
            FaqItem("Can I track the progress of my report?", "Yes, the app allows you to view the status of your submitted reports, categorized as pending, in progress, or resolved."),
            FaqItem("How do I filter my reports?", "Use the filter option on the 'View Submitted Reports' page to sort reports by status or date."),
            FaqItem("Will I receive updates about my report?", "Yes, the app sends real-time notifications about the status of your report through Firebase Notifications."),
            FaqItem("Can I provide feedback on IT support?", "After your report is resolved, you will receive a notification prompting you to provide feedback on your experience."),
            FaqItem("Can I edit a report after submission?", "No, submitted reports cannot be edited. However, you can submit a new report with the correct details if needed."),
            FaqItem("How do I know if my report was successfully submitted?", "You will receive a confirmation notification, and the report will appear in your 'View Submitted Reports' list."),
            FaqItem("What is the maximum number of images I can attach to a report?", "You can attach up to 5 images per report. Ensure they clearly capture the issue to assist IT Services in resolving it faster."),
            FaqItem("Can I view reports submitted by others?", "No, reports are private and can only be viewed by the user who submitted them and the IT team."),
            FaqItem("I forgot my password.", "Use the 'Forgot Password' feature on the login page to reset your password via your DLSU email."),
            FaqItem("I cannot log in. What should I do?", "Ensure you are using your registered DLSU email and password. If issues persist, contact the IT department for further assistance."),
            FaqItem("What happens if I lose internet connectivity while submitting a report?", "AnimoAssist will save your report locally and automatically sync it to the server when connectivity is restored."),
            FaqItem("I didn’t receive a notification about my report. What can I do?", "Ensure that notifications are enabled for AnimoAssist in your device settings. You can also manually check your report’s status within the app.")
        )

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


        // Set up drawer item click listeners
        setupDrawerItemListeners()
        // Set the layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set the adapter
        recyclerView.adapter = FaqAdapter(faqList)

        val margin = resources.getDimensionPixelSize(R.dimen.item_margin)  // Set this in dimens.xml or a fixed value
        recyclerView.addItemDecoration(MarginItemDecoration(margin))
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



