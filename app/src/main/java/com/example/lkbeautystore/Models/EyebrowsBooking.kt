package com.example.lkbeautystore.Models

data class EyebrowsBooking(
    val bookingId: String = "",
    val userId: String = "",
    val userName: String = "",
    val serviceName: String = "",
    val serviceDescription: String = "",
    val amount: String = "",
    val date: String = "",
    val timeSlot: String = "",
    val status: String = "Pending" // Approved / Declined set by admin later
)
