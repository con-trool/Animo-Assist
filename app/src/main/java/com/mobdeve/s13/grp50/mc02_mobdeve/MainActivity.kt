package com.mobdeve.s13.grp50.mc02_mobdeve

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Set window insets for full screen layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get the "Sign in" button
        val signInButton = findViewById<Button>(R.id.btnSubmit)

        // Set an onClickListener for the "Sign in" button
        signInButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java) // Adjust to your SignInActivity class name
            startActivity(intent)
        }

        // Get the "Create an account" TextView
        val createAccountTextView = findViewById<TextView>(R.id.create_an_account)

        // Set an onClickListener for the "Create an account" TextView
        createAccountTextView.setOnClickListener {
            val intent = Intent(this, SignupPage::class.java) // Adjust to your SignUpPage class name
            startActivity(intent)
        }
    }
}
