package com.mobdeve.s13.grp50.mc02_mobdeve

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.Locale


class ReportPage : AppCompatActivity() {

    private lateinit var btnAddImage: ImageView
    private lateinit var btnRemoveImage: ImageView
    private lateinit var btnSubmit: Button
    private lateinit var spinnerLocation: Spinner
    private lateinit var etDescription: EditText
    private lateinit var etFloorRoom: AutoCompleteTextView
    private lateinit var drawerLayout: DrawerLayout

    private var selectedImageUri: Uri? = null
    private lateinit var tempFile: File
    private var userEmail: String? = null
    private lateinit var menuIcon: ImageView
    private lateinit var txtReportType: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        btnAddImage = findViewById(R.id.btnAddImage)
        btnRemoveImage = findViewById(R.id.btnRemoveImage)
        btnSubmit = findViewById(R.id.btnSubmit)
        spinnerLocation = findViewById(R.id.spinnerLocation3)
        etDescription = findViewById(R.id.editTextTextMultiLine)
        etFloorRoom = findViewById(R.id.editTextFloorRoom)
        drawerLayout = findViewById(R.id.drawer_layout)
        menuIcon = findViewById(R.id.menuIcon)

        val reportType = intent.getStringExtra("REPORT_TYPE")
        val editTextFloorRoom: AutoCompleteTextView = findViewById(R.id.editTextFloorRoom)


        // Generate allowed numbers: 101-110, 201-210, ..., 2001-2010
        val allowedNumbers = ArrayList<String>()
        for (floor in 1..20) {
            for (room in 1..10) {
                allowedNumbers.add(floor.toString() + "0" + room)
            }
        }


        // Set up the adapter for the AutoCompleteTextView
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, allowedNumbers)
        editTextFloorRoom.setAdapter(adapter)
        // Display the report type (optional)
        txtReportType = findViewById(R.id.txtReportType)
        txtReportType.text = reportType
        // Handle the menu icon click to open/close the drawer
        menuIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        setupDrawerItemListeners()
        userEmail = getSharedPreferences("UserDetails", Context.MODE_PRIVATE).getString("USER_EMAIL", null)
        btnAddImage.setOnClickListener { showImagePickerDialog() }
        btnRemoveImage.setOnClickListener { removeSelectedImage() }
        btnSubmit.setOnClickListener { submitReport() }
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

    private fun showImagePickerDialog() {
        val options = arrayOf("Open Camera", "Open Gallery")
        AlertDialog.Builder(this).apply {
            setTitle("Select Image")
            setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> openImageSelector()
                }
            }
            show()
        }
    }

    private fun openCamera() {
        tempFile = File(cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        try {
            if (!tempFile.exists()) {
                tempFile.createNewFile()
            }
            val photoURI = FileProvider.getUriForFile(this, "${applicationContext.packageName}.provider", tempFile)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            selectedImageUri = photoURI
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("ReportPage", "Error creating file for camera capture")
        }
    }

    private fun openImageSelector() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_PICK -> {
                    selectedImageUri = data?.data
                    if (selectedImageUri != null) {
                        btnAddImage.setImageURI(selectedImageUri)
                        btnRemoveImage.visibility = ImageView.VISIBLE
                        Log.d("ReportPage", "Image selected: $selectedImageUri")
                    } else {
                        Log.e("ReportPage", "Image selection failed: URI is null")
                    }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    if (tempFile.exists()) {
                        selectedImageUri = Uri.fromFile(tempFile)
                        btnAddImage.setImageURI(selectedImageUri)
                        btnRemoveImage.visibility = ImageView.VISIBLE
                        Log.d("ReportPage", "Image captured and exists: $selectedImageUri")
                    } else {
                        Log.e("ReportPage", "Image capture failed: File does not exist")
                    }
                }
            }
        }
    }

    private fun removeSelectedImage() {
        selectedImageUri = null
        btnAddImage.setImageResource(R.drawable.addimage)
        btnRemoveImage.visibility = ImageView.GONE
    }

    private fun submitReport() {
        val description = etDescription.text.toString().trim()
        val floorRoom = etFloorRoom.text.toString().trim()
        val location = spinnerLocation.selectedItem.toString()

        if (description.isEmpty() || floorRoom.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (selectedImageUri != null) {
            uploadImageToCloudinary(selectedImageUri!!, description, floorRoom, location)
        } else {
            saveReportToFirebase(description, floorRoom, location, null)
        }
    }

    private fun saveReportToFirebase(description: String, floorRoom: String, location: String, imageUrl: String?) {
        val reportType = intent.getStringExtra("REPORT_TYPE") ?: "General Concern" // Default if not provided
        val status = "Pending" // Default status

        // Retrieve email from Intent
        val email = intent.getStringExtra("USER_EMAIL")

        if (email == null) {
            Log.e("ReportPage", "User email is missing. Cannot submit report.")
            Toast.makeText(this, "User email is missing. Cannot submit report.", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("ReportPage", "Submitting report for email: $email")

        // Format the current date and time
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val timestamp = dateFormat.format(System.currentTimeMillis()) // e.g., "2024-11-23 13:45:00"

        val reportData = hashMapOf(
            "description" to description,
            "floorRoom" to floorRoom,
            "location" to location,
            "imageUrl" to imageUrl,
            "timestamp" to timestamp,
            "numericTimestamp" to System.currentTimeMillis(),
            "reportType" to reportType,
            "status" to status,
            "email" to email // Save the email
        )

        FirebaseFirestore.getInstance().collection("Reports")
            .add(reportData)
            .addOnSuccessListener {
                runOnUiThread {
                    AlertDialog.Builder(this)
                        .setTitle("Report Submitted")
                        .setMessage("Your report has been submitted successfully. You will now be redirected to the homepage.")
                        .setPositiveButton("OK") { _, _ ->
                            val intent = Intent(this, MainMenu::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                        .setCancelable(false)
                        .show()
                }
            }
            .addOnFailureListener {
                runOnUiThread {
                    Toast.makeText(this, "Failed to save report to Firebase", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun uploadImageToCloudinary(imageUri: Uri, description: String, floorRoom: String, location: String) {
        val cloudName = "drd5ynf0u" // Replace with your Cloudinary cloud name
        val apiKey = "717182649557488" // Replace with your API key
        val apiSecret = "SZA2VVZkkhF352mQZ6j3hX5pn68" // Replace with your API secret
        val uploadPreset = "AnimoAssist" // Replace with your actual upload preset

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val inputStream = contentResolver.openInputStream(imageUri)
                val imageData = inputStream?.readBytes() ?: throw IOException("Failed to read image data")

                val url = "https://api.cloudinary.com/v1_1/$cloudName/image/upload"
                val requestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", "image.jpg", imageData.toRequestBody())
                    .addFormDataPart("upload_preset", uploadPreset)
                    .build()

                val request = Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build()

                val client = OkHttpClient()
                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val imageUrl = parseImageUrlFromResponse(responseBody)
                    if (imageUrl != null) {
                        Log.d("Cloudinary", "Image uploaded successfully: $imageUrl")
                        withContext(Dispatchers.Main) {
                            saveReportToFirebase(description, floorRoom, location, imageUrl)
                        }
                    }
                } else {
                    Log.e("Cloudinary", "Image upload failed: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("Cloudinary", "Error uploading image: ${e.message}", e)
            }
        }
    }


    private fun parseImageUrlFromResponse(responseBody: String?): String? {
        return try {
            val jsonObject = JSONObject(responseBody)
            jsonObject.getString("secure_url")
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        private const val REQUEST_IMAGE_PICK = 1001
        private const val REQUEST_IMAGE_CAPTURE = 1002
    }
}
