package com.example.lkbeautystore.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.lkbeautystore.Models.EyebrowsBooking
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CustBookingViewModel: ViewModel() {
    val bookings = mutableStateListOf<EyebrowsBooking>()
    private val auth = FirebaseAuth.getInstance()
    private val dbRef = FirebaseDatabase.getInstance().getReference("Bookings")

    fun fetchBookings(context: Context) {
        val user = auth.currentUser ?: return

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookings.clear()
                for (bookingSnapshot in snapshot.children) {
                    val booking = bookingSnapshot.getValue(EyebrowsBooking::class.java)
                    if (booking?.userId == user.uid) {
                        booking?.let { bookings.add(it) }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to load bookings", error.toException())
            }
        })

    }
}