package com.mobdeve.s13.grp50.mc02_mobdeve

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Check if an email exists in SharedPreferences
        val sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("USER_EMAIL", null)

        if (userEmail != null) {
            // Redirect to MainMenu if email exists
            val intent = Intent(this, MainMenu::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) // Clear the activity stack
            startActivity(intent)
            finish()
            return
        }

        // Set window insets for full-screen layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get the "Sign in" button
        val signInButton = findViewById<Button>(R.id.btnSubmit)

        // Set an onClickListener for the "Sign in" button
        signInButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Get the "Create an account" TextView
        val createAccountTextView = findViewById<TextView>(R.id.create_an_account)

        // Set an onClickListener for the "Create an account" TextView
        createAccountTextView.setOnClickListener {
            val intent = Intent(this, SignupPage::class.java)
            startActivity(intent)
        }
    }
}
