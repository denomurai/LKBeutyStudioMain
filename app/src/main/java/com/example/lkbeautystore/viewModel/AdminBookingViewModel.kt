package com.example.lkbeautystore.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.lkbeautystore.Models.EyebrowsBooking
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminBookingViewModel: ViewModel() {
    val bookings = mutableStateListOf<EyebrowsBooking>()
    private val dbRef = FirebaseDatabase.getInstance().getReference("Bookings")

    fun fetchAllBookings(context: Context) {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookings.clear()
                for (bookingSnap in snapshot.children) {
                    val booking = bookingSnap.getValue(EyebrowsBooking::class.java)
                    if (booking != null) {
                        val id = bookingSnap.key ?: ""
                        val updatedBooking = booking.copy(bookingId = id)
                        bookings.add(updatedBooking)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to load bookings", error.toException())
            }
        })
    }

    fun updateBookingStatus(bookingId: String, newStatus: String) {
        dbRef.child(bookingId).child("status").setValue(newStatus)
    }
}