package com.example.lkbeautystore.ui.theme.CustomerScreens

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lkbeautystore.viewModel.EyebrowsBookingViewModel
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bookingEyebrowsScreen (
    navController: NavController,
    serviceName: String,
    serviceDescription: String,
    amount: String
){
    val eyebrowsBookingViewModel : EyebrowsBookingViewModel = viewModel ()
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    var selectedDate by remember { mutableStateOf("") }
    var selectedTimeSlot by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val timeSlots = listOf(
        "8 AM - 10 AM",
        "10 AM - 12 PM",
        "12 PM - 2 PM",
        "2 PM - 4 PM",
        "4 PM - 6 PM"
    )

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Service") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB519A0),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "User: ${user?.displayName ?: user?.email ?: "Customer"}",
                fontWeight = FontWeight.Bold
            )

            Text("Service: $serviceName", fontWeight = FontWeight.Bold)
            Text("Description: $serviceDescription")
            Text("Amount: $amount")

            Spacer(modifier = Modifier.height(12.dp))

            // Date picker
            OutlinedTextField(
                value = selectedDate,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Date") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    TextButton(onClick = { datePickerDialog.show() }) {
                        Text("Pick")
                    }
                }
            )

            // Time Slot Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedTimeSlot,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Time Slot") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    timeSlots.forEach { slot ->
                        DropdownMenuItem(
                            text = { Text(slot) },
                            onClick = {
                                selectedTimeSlot = slot
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    when {
                        selectedDate.isBlank() -> {
                            android.widget.Toast.makeText(context, "Please select a date", android.widget.Toast.LENGTH_LONG).show()
                        }
                        selectedTimeSlot.isBlank() -> {
                            android.widget.Toast.makeText(context, "Please select a time slot", android.widget.Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            eyebrowsBookingViewModel.bookService(
                                navController,
                                context = context,
                                serviceName = serviceName,
                                serviceDescription = serviceDescription,
                                amount = amount,
                                date = selectedDate,
                                timeSlot = selectedTimeSlot
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB519A0))
            ) {
                Text("Book Service", color = Color.White)
            }
        }
    }

}