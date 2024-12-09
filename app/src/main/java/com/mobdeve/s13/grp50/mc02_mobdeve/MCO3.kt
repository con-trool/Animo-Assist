package com.mobdeve.s13.grp50.mc02_mobdeve

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MCO3 : Application() {
    override fun onCreate() {
        super.onCreate()

        val firestore = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true) // Example setting
            .build()

        firestore.firestoreSettings = settings
    }
}
