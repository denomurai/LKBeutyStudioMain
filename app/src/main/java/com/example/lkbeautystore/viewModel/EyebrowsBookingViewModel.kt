package com.example.lkbeautystore.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.lkbeautystore.Models.EyebrowsBooking
import com.example.lkbeautystore.navigation.ROUTE_USER_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class EyebrowsBookingViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val dbRef = FirebaseDatabase.getInstance().getReference("Bookings")

    fun bookService(
        navController: NavController,
        context: Context,
        serviceName: String,
        serviceDescription: String,
        amount: String,
        date: String,
        timeSlot: String
    ) {
        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(context, "Please log in first", Toast.LENGTH_LONG).show()
            navController.navigate(ROUTE_USER_LOGIN) // Navigate to login screen
            return
        }

        val userId = user.uid
        val userName = user.displayName ?: user.email ?: "Customer"

        if (date.isBlank() || timeSlot.isBlank()) {
            Toast.makeText(context, "Please select date and time", Toast.LENGTH_LONG).show()
            return
        }

        val bookingId = UUID.randomUUID().toString()
        val booking = EyebrowsBooking(
            bookingId = bookingId,
            userId = userId,
            userName = userName,
            serviceName = serviceName,
            serviceDescription = serviceDescription,
            amount = amount,
            date = date,
            timeSlot = timeSlot
        )

        dbRef.child(bookingId).setValue(booking)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Service booked successfully!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Failed to book service", Toast.LENGTH_LONG).show()
                }
            }
    }
}