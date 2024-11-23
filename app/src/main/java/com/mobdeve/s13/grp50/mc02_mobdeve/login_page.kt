package com.mobdeve.s13.grp50.mc02_mobdeve

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Find views by their IDs
        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword2)
        val loginButton = findViewById<Button>(R.id.loginbtn)
        val signUpTextView = findViewById<TextView>(R.id.signUp)

        // Set OnClickListener for the login button
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validate inputs
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Attempt to log in the user
            loginUser(email, password)
        }

        // Set OnClickListener for the sign-up text
        signUpTextView.setOnClickListener {
            // Navigate to SignUpPage activity when sign-up text is clicked
            val intent = Intent(this, SignupPage::class.java)
            startActivity(intent)
        }
    }
    private fun loginUser(email: String, password: String) {
        // Hash the input password
        val hashedPassword = hashPassword(password)

        // Query Firestore to validate email and password
        firestore.collection("Users")
            .whereEqualTo("email", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documents = task.result
                    if (!documents.isEmpty) {
                        val user = documents.documents[0]
                        val storedPassword = user.getString("password")

                        if (storedPassword == hashedPassword) {
                            // Login successful
                            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

                            // Save email to SharedPreferences
                            val sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("USER_EMAIL", email)
                            editor.apply()

                            // Proceed to MainMenu
                            val intent = Intent(this, MainMenu::class.java)
                            startActivity(intent)

                        } else {
                            // Password does not match
                            Toast.makeText(this, "Invalid password. Please try again.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Email not found
                        Toast.makeText(this, "Email not registered. Please sign up.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Firestore query failed
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }



    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) } // Convert bytes to hex string
    }
}
