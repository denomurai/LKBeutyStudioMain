package com.example.lkbeautystore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.lkbeautystore.navigation.AppNavHost
import com.example.lkbeautystore.navigation.ROUTE_SPLASH
import com.example.lkbeautystore.navigation.ROUTE_USER_LOGIN
import com.example.lkbeautystore.navigation.Routes
import com.example.lkbeautystore.ui.theme.LkBeautyStoreTheme
import com.google.firebase.auth.FirebaseAuth


import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LkBeautyStoreTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()

    //  Handle back button: close drawer instead of exiting app
    androidx.activity.compose.BackHandler(enabled = drawerState.isOpen) {
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(16.dp))
                Divider()

                TextButton (onClick = {
                    navController.navigate(Routes.EyebrowsService)
                    scope.launch { drawerState.close() }
                }) { Text("Eyebrows Service") }

                TextButton(onClick = {
                    navController.navigate(Routes.BookingHistory)
                    scope.launch { drawerState.close() }
                }) { Text("Booking History") }

                Divider()

                TextButton(onClick = {
                    auth.signOut()
                    navController.navigate(ROUTE_USER_LOGIN) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }//closes app
                    }
                    scope.launch { drawerState.close() }
                }) { Text("Log Out") }
            }
        }
    ) {
        AppNavHost(
            navController = navController,
            openDrawer = { scope.launch { drawerState.open() } },
            scope = scope
        )



    }
}