package com.example.lkbeautystore.ui.theme.CustomerScreens

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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.lkbeautystore.viewModel.ProductViewmodel

import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.text.font.FontWeight


// customer View Eyebrows Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun custViewEybrowsScreen (navController: NavController){
    val productViewModel: ProductViewmodel = viewModel()
    val context = LocalContext.current
    val eyebrows = productViewModel.eyebrows

    // Fetch eyebrow data
    LaunchedEffect(Unit) {
        productViewModel.fetchEyebrows(context)
    }

    // Screen layout with a top bar
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Eyebrow Services") },
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

        if (eyebrows.isEmpty()) {
            // Empty state UI
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFF8F8F8)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Eyebrow Services Available",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Gray
                )
            }
        } else {
            // Show eyebrow list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFF8F8F8)),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(eyebrows) { eyebrow ->
                    EyebrowsCard(eyebrow, navController)
                }
            }
        }
    }
}

@Composable
fun EyebrowsCard(
    eyebrows: EyebrowsModel,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = eyebrows.imageUrl,
                    contentDescription = "Service Image",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color(0xFF28A9B5), CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = eyebrows.name.ifEmpty { "Unnamed Service" },
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFF333333)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Ksh ${eyebrows.amount}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF28A9B5),
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = eyebrows.description.ifEmpty { "No description available." },
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        val nameEncoded = java.net.URLEncoder.encode(eyebrows.name, "UTF-8")
                        val descEncoded = java.net.URLEncoder.encode(eyebrows.description, "UTF-8")
                        val amountEncoded =
                            java.net.URLEncoder.encode(eyebrows.amount.toString(), "UTF-8")

                        navController.navigate("bookService/$nameEncoded/$descEncoded/$amountEncoded")
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF28A9B5))
                ) {
                    Text(
                        text = "Book Service",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }

}