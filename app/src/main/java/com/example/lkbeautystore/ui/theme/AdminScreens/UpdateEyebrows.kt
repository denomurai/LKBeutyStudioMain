package com.example.lkbeautystore.ui.theme.AdminScreens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.lkbeautystore.Models.EyebrowsModel
import com.example.lkbeautystore.Models.LipServiceModel
import com.example.lkbeautystore.viewModel.ProductViewmodel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

@Composable
fun UpdateEyebrows(navController: NavController, id: String) {
    val productViewmodel: ProductViewmodel = viewModel()
    val context = LocalContext.current
    var lipService by remember { mutableStateOf<LipServiceModel?>(null) }
    var isUpdating by remember { mutableStateOf(false) }

    // Fetch service
    LaunchedEffect(id) {
        try {
            val snapshot = FirebaseDatabase.getInstance()
                .getReference("lipServices")
                .child(id)
                .get()
                .await()
            lipService = snapshot.getValue(LipServiceModel::class.java)?.copy(id = id)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to load service", Toast.LENGTH_SHORT).show()
        }
    }

    if (lipService == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    var name by remember { mutableStateOf(lipService!!.name) }
    var amount by remember { mutableStateOf(lipService!!.amount.toString()) }
    var description by remember { mutableStateOf(lipService!!.description) }

    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri.value = uri
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFFFF3E0), Color(0xFFF8BBD0))))
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Update Lip Service", fontSize = 24.sp, color = Color(0xFFAD1457))

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(140.dp)
                        .clickable { launcher.launch("image/*") }
                        .shadow(8.dp, CircleShape)
                ) {
                    AnimatedContent(targetState = imageUri.value, label = "Image Picker Animation") { it ->
                        AsyncImage(
                            model = it ?: lipService!!.imageUrl,
                            contentDescription = "Lip Service Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text("Tap to change picture", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(20.dp))

                val fieldModifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Service Name") }, modifier = fieldModifier)
                OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Cost of Service") }, modifier = fieldModifier)
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = fieldModifier)

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = { navController.popBackStack() }, colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)) {
                        Text("Go Back", color = Color.DarkGray)
                    }

                    Button(onClick = {
                        isUpdating = true
                        productViewmodel.updateLipService(
                            id = id,
                            imageUri = imageUri.value,
                            name = name,
                            amount = amount.toDoubleOrNull() ?: 0.0,
                            description = description,
                            context = context,
                            onSuccess = {
                                isUpdating = false
                                Toast.makeText(context, "Service updated successfully", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            },
                            onError = { error ->
                                isUpdating = false
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB519A0))) {
                        Text("Update", color = Color.White)
                    }
                }
            }
        }

        if (isUpdating) Box(modifier = Modifier.fillMaxSize().background(Color(0x88000000)), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.White)
        }
    }
}