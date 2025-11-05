package com.example.lkbeautystore.ui.theme.AdminScreens

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lkbeautystore.Models.EyebrowsBooking
import com.example.lkbeautystore.viewModel.AdminBookingViewModel

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun adminViewBookingsScreen(navController: NavController) {
    val adminViewModel: AdminBookingViewModel = viewModel ()
    val context = LocalContext.current
    val bookings = adminViewModel.bookings

    LaunchedEffect(Unit) {
        adminViewModel.fetchAllBookings(context)
    }

    // 1️⃣ Search state
    var searchDate by remember { mutableStateOf("") }

    // 🔹 Date picker setup
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, day)
            searchDate = selectedDate
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // 2️⃣ Filtered list based on search
    val filteredBookings = if (searchDate.isBlank()) {
        bookings
    } else {
        bookings.filter { it.date.contains(searchDate) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Eyebrow Bookings") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFB519A0), titleContentColor = Color.White)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF4F4F4)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 🔹 Search field with calendar icon
            item {
                OutlinedTextField(
                    value = searchDate,
                    onValueChange = { searchDate = it },
                    label = { Text("Select booking date") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton (onClick = { datePickerDialog.show() }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Pick Date",
                                tint = Color(0xFFB519A0)
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerDialog.show() }
                )

                if (searchDate.isNotBlank()) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(
                            onClick = { searchDate = "" },
                        ) {
                            Text("Clear Date Filter", color = Color.Gray)
                        }
                    }
                }
            }

            // Display filtered bookings
            items(filteredBookings) { booking ->
                BookingCard(
                    booking = booking,
                    onStatusChange = { status ->
                        adminViewModel.updateBookingStatus(booking.bookingId, status)
                    }
                )
            }

            // Empty state if no bookings found
            if (filteredBookings.isEmpty()) {
                item {
                    Text(
                        text = "No bookings found",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun BookingCard(booking: EyebrowsBooking, onStatusChange: (String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Customer ID: ${booking.userId}")
            Text("Customer Name: ${booking.userName}")
            Text("Service: ${booking.serviceName}")
            Text("Amount: ${booking.amount}")
            Text("Date: ${booking.date}")
            Text("Status: ${booking.status}", color = when (booking.status) {
                "Approved" -> Color(0xFF2E7D32)
                "Declined" -> Color(0xFFC62828)
                else -> Color(0xFF757575)
            })

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),  // only spacing
                verticalAlignment = Alignment.CenterVertically        // vertical alignment
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onStatusChange("Approved") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text("Approve")
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { onStatusChange("Declined") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
                ) {
                    Text("Decline")
                }
            }

        }
    }
}