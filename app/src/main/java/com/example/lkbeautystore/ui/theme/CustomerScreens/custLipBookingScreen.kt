package com.example.lkbeautystore.ui.theme.CustomerScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lkbeautystore.Models.EyebrowsBooking
import com.example.lkbeautystore.viewModel.CustBookingViewModel
import kotlin.text.ifEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun custLipBookingScreen(navController: NavController) {
    val viewModel: CustBookingViewModel = viewModel()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchBookings(context)
    }

    val bookings = viewModel.bookings


    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("My Bookings") },
                navigationIcon = {
                    IconButton (onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF28A9B5),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        if (bookings.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "You have no bookings yet.",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFF5F5F5)),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // ✅ Pass cancel action to the card
                items(bookings) { booking ->
                    LipBookingCard(
                        booking = booking,
                        onCancel = { navController.popBackStack() }
                    )

                }
            }
        }
    }
}

@Composable
fun LipBookingCard(booking: EyebrowsBooking,
                   onCancel: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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

            Spacer(modifier = Modifier.height(12.dp))

            // ✅ Cancel Button
            Button (
                onClick = { onCancel() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel", color = Color.White)
            }
        }
    }
}