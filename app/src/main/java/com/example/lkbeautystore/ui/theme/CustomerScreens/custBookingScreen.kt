package com.example.lkbeautystore.ui.theme.CustomerScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lkbeautystore.Models.EyebrowsBooking
import com.example.lkbeautystore.viewModel.CustBookingViewModel
import androidx.compose.foundation.lazy.items


// Screen to view Spesific customer bookings
@Composable
fun custBookingScreen(navController: NavController){
    val viewModel: CustBookingViewModel = viewModel ()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchBookings(context)
    }

    val bookings = viewModel.bookings


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(bookings) { booking ->
            BookingCard(booking)
        }
    }
}

@Composable
fun BookingCard(booking: EyebrowsBooking) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Service: ${booking.serviceName}", style = MaterialTheme.typography.titleMedium)
            Text("Amount: ${booking.amount}", style = MaterialTheme.typography.bodyMedium)
            Text("Date: ${booking.date}", style = MaterialTheme.typography.bodyMedium)
            Text("Time: ${booking.timeSlot}", style = MaterialTheme.typography.bodyMedium)
            Text(
                "Status: ${booking.status.ifEmpty { "Pending" }}",
                style = MaterialTheme.typography.bodyMedium,
                color = when (booking.status.lowercase()) {
                    "approved" -> Color(0xFF4CAF50) // green
                    "rejected" -> Color(0xFFF44336) // red
                    else -> Color(0xFFFFA000) // amber
                }
            )
        }
    }
}