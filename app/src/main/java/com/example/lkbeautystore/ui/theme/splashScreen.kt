package com.example.lkbeautystore.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lkbeautystore.R

@Composable
fun SplashScreen(onNavigateToNext:() -> Unit){
    val splashScreenDuration =3000L
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(splashScreenDuration)
        onNavigateToNext()
    }
    Box(modifier = Modifier.fillMaxSize().background(Color.Black),
        contentAlignment = Alignment.Center){
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Image(painter = painterResource(R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(300.dp))
            Text("Welcome to",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp)
            Text("LK BEAUTY STUDIO",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp)
        }
    }
}