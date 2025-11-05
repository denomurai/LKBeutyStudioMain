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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.lkbeautystore.Models.EyebrowsModel
import com.example.lkbeautystore.navigation.ROUTE_ADMIN_UPDATE_Eyebrows
import com.example.lkbeautystore.viewModel.ProductViewmodel
import androidx.compose.foundation.lazy.items


@Composable
fun viewEyebrowsScreen (navController: NavController){
    val productViewmodel: ProductViewmodel=viewModel ()

    val context = LocalContext.current
    val eyebrows=productViewmodel.eyebrows
    var isLoading by remember { mutableStateOf(true) }  //  loading state

    LaunchedEffect (Unit){
        productViewmodel.fetchEyebrows(context)
        isLoading = false  // once fetch finishes
    }
    when {
        isLoading -> {
            //  Show loading spinner
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.CircularProgressIndicator()
            }
        }

        eyebrows.isEmpty() -> {
            //  Show message if list is empty after loading
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No eyebrow services found.",
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        else -> {
            // ✅ Show the list when data is available
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentPadding = PaddingValues(vertical = 50.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(eyebrows) { eyebrow ->
                    EyebrowsCard(
                        eyebrows = eyebrow,
                        onDelete = { id -> productViewmodel.deleteEyebrows(id, context) },
                        navController = navController
                    )
                }
            }
        }
    }



}




@Composable
fun EyebrowsCard(
    eyebrows: EyebrowsModel,
    onDelete: (String) -> Unit,
    navController: NavController
){
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Delete") },
            text = { Text("Are you sure you want to delete this eyebrow service?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onDelete(eyebrows.id)
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                eyebrows.imageUrl?.let { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Employee Image",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = eyebrows.name ?: "No name",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Amount: ${eyebrows.amount ?: "N/A"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Description: ${eyebrows.description ?: "N/A"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = {
                            navController.navigate("${ROUTE_ADMIN_UPDATE_Eyebrows}/${eyebrows.id}")
                        }) {
                            Text(
                                text = "Update",
                                color = MaterialTheme.colorScheme.surface,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }


                        Spacer(modifier = Modifier.width(8.dp))

                        Button(onClick = { showDialog = true }) {
                            Text(
                                text = "Delete",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
