package com.mobdeve.s13.grp50.mc02_mobdeve

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.mobdeve.s13.grp50.mc02_mobdeve.databinding.ActivitySignupPageBinding
import java.security.MessageDigest

class SignupPage : AppCompatActivity() {

    private lateinit var binding: ActivitySignupPageBinding
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivitySignupPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()


        // Set click listener for the Register button
        binding.registerbtn.setOnClickListener {
            val firstName = binding.editTextfirstname.text.toString().trim()
            val lastName = binding.editTextlastname.text.toString().trim()
            val idNum = binding.editTextidnum.text.toString().trim()
            val course = binding.editTextcourse.text.toString().trim()
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            // Validate inputs
            if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(idNum) ||
                TextUtils.isEmpty(course) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if the email already exists
            checkEmailExistence(email) { exists ->
                if (exists) {
                    binding.emailWarningText.visibility = android.view.View.VISIBLE
                } else {
                    // Hash the password before saving
                    val hashedPassword = hashPassword(password)
                    // Save user data directly to Firestore
                    saveUserData(firstName, lastName, idNum, course, email, hashedPassword)
                }
            }
        }

        // Set click listener for the "Sign In" TextView
        binding.signin.setOnClickListener {
            // Navigate to the Login page
            val intent = Intent(this, LoginActivity::class.java) // Replace LoginActivity with your actual login activity class
            startActivity(intent)

        }

    }

    private fun checkEmailExistence(email: String, callback: (Boolean) -> Unit) {
        firestore.collection("Users")
            .whereEqualTo("email", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && !task.result.isEmpty) {
                    callback(true) // Email already exists
                } else {
                    callback(false) // Email is available
                }
            }
    }

    private fun saveUserData(
        firstName: String,
        lastName: String,
        idNum: String,
        course: String,
        email: String,
        password: String
    ) {
        // Create a new document in the "Users" collection
        val userRef = firestore.collection("Users").document()

        val userData = mapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "idNum" to idNum,
            "course" to course,
            "email" to email,
            "password" to password // Store the hashed password
        )

        userRef.set(userData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                finish() // Close the activity or redirect to another page
            } else {
                Toast.makeText(this, "Failed to save user data: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) } // Convert bytes to hex string
    }
}
