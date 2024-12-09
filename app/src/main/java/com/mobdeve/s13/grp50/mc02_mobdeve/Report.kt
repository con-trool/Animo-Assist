package com.mobdeve.s13.grp50.mc02_mobdeve

data class Report(
    val id: String,
    val rating: Int? = null,
    val description: String,
    val title: String,
    val status: String,
    val timestamp: Long,
    val floorRoom: String,
    val location: String,
    val imageUrl: String?
)
