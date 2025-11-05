package com.example.lkbeautystore.ui.theme.AdminScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lkbeautystore.Models.LashesModel
import com.example.lkbeautystore.navigation.ROUTE_ADMIN_UPDATE_LASHES
import com.example.lkbeautystore.viewModel.ProductViewmodel

@Composable
fun viewLashesScreen(navController: NavController) {
    val productViewmodel: ProductViewmodel = viewModel()
    val context = LocalContext.current
    val lashesServices = productViewmodel.lashesServicesl

    var isLoading by remember { mutableStateOf(true) }

    // Fetch Lashes Services
    LaunchedEffect(Unit) {
        productViewmodel.fetchlashesServices(context)
        isLoading = false
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        lashesServices.isEmpty() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No lashes services found.",
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF0F0F0)),
                contentPadding = PaddingValues(vertical = 50.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(lashesServices, key = { it.id }) { service ->
                    LashesCard(
                        service = service,
                        navController = navController,
                        onDelete = { productViewmodel.deleteLashesService(it, context) }
                    )
                }
            }
        }
    }
}

@Composable
fun LashesCard(
    service: LashesModel,
    navController: NavController,
    onDelete: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Delete") },
            text = { Text("Are you sure you want to delete this lashes service?") },
            confirmButton = {
                TextButton (onClick = {
                    showDialog = false
                    onDelete(service.id)
                }) { Text("Yes") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column (modifier = Modifier.padding(16.dp)) {
            Row (verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = service.imageUrl,
                    contentDescription = "Lashes Service Image",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color(0xFFB519A0), CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = service.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Amount: ${service.amount}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Description: ${service.description}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = {
                            navController.navigate("${ROUTE_ADMIN_UPDATE_LASHES}/${service.id}")
                        }) {
                            Text("Update", color = Color.White)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = { showDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text(
                                text = "Delete",
                                color = Color(0xFFB519A0)
                            )
                        }
                    }
                }
            }
        }
    }
}