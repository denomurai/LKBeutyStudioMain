package com.example.lkbeautystore.ui.theme.AdminScreens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lkbeautystore.R
import com.example.lkbeautystore.navigation.ROUTE_ADD_Eyebrows
import com.example.lkbeautystore.navigation.ROUTE_ADD_LASHES
import com.example.lkbeautystore.navigation.ROUTE_ADD_Lip

import com.example.lkbeautystore.navigation.ROUTE_ADMIN_VIEW_EYEBROWS
import com.example.lkbeautystore.navigation.ROUTE_ADMIN_VIEW_EYEBROWS_BOOKINGS
import com.example.lkbeautystore.navigation.ROUTE_ADMIN_VIEW_LASHES
import com.example.lkbeautystore.navigation.ROUTE_ADMIN_VIEW_LIP

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun adminDashboard (navController: NavController){

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Image(painter = painterResource(id = R.drawable.logo),
                        contentDescription = "App log",
                        modifier = Modifier.size(140.dp))
                    Spacer(modifier = Modifier.height(24.dp))
                    //Text("LK Beauty Studio", fontSize = 24.sp)
                },

                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(color = +0xff28a9b5), titleContentColor = Color.White)
                ,
                actions = {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Log out", modifier = Modifier.size(40.dp), Color.White)
                })

        },
        bottomBar = {
            NavigationBar(containerColor = Color(color = +0xff28a9b5), )
            {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "\u00A9 Lk Beauty Studio 2025", modifier = Modifier.padding(16.dp),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                    /*Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End){

                        TextButton(onClick = {navController.navigate(ROUTE_ADMIN_LOGIN)}) { Text("Admin Login", modifier = Modifier) }
                    }*/
                }
            }
        }
    ){ innerPadding ->
        val scrollState = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "SERVICES",
                    modifier = Modifier,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(28.dp))

                Card(
                    modifier = Modifier.padding(vertical = 15.dp, horizontal = 8.dp).fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(10.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(color = 0xFFB519A0))
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        /* Icon(Icons.Default.Person, contentDescription = "Add Employee", modifier = Modifier.size(40.dp), Color(0xFF004D40))
                        Spacer(modifier = Modifier.width(20.dp))*/

                        Text("EYEBROWS SERVICES", fontSize = 20.sp, color = Color.White)
                        Spacer(modifier = Modifier.size(13.dp))
                        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                            Button(
                                onClick = {navController.navigate(ROUTE_ADMIN_VIEW_EYEBROWS)},
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                            )
                            { Text("VIEW SERVICES") }
                            Button(
                                onClick = { navController.navigate(ROUTE_ADD_Eyebrows) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                            )
                            { Text("ADD SERVICE") }
                        }

                    }
                }// End of Eyebrows

                Card(
                    modifier = Modifier.padding(vertical = 15.dp, horizontal = 8.dp).fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(10.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(color = 0xFFB519A0))
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        /* Icon(Icons.Default.Person, contentDescription = "Add Employee", modifier = Modifier.size(40.dp), Color(0xFF004D40))
                         Spacer(modifier = Modifier.width(20.dp))*/

                        Text("LIP SERVICES", fontSize = 20.sp, color = Color.White)
                        Spacer(modifier = Modifier.size(13.dp))
                        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                            Button(
                                onClick = {navController.navigate(ROUTE_ADMIN_VIEW_LIP)},
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                            )
                            { Text("VIEW SERVICES") }
                            Button(
                                onClick = {navController.navigate(ROUTE_ADD_Lip)  },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                            )
                            { Text("ADD LIP") }
                        }

                    }
                }//End of Lips services

                Card(
                    modifier = Modifier.padding(vertical = 15.dp, horizontal = 8.dp).fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(10.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(color = 0xFFB519A0))
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        /* Icon(Icons.Default.Person, contentDescription = "Add Employee", modifier = Modifier.size(40.dp), Color(0xFF004D40))
                         Spacer(modifier = Modifier.width(20.dp))*/

                        Text("EYE LASHES SERVICES", fontSize = 20.sp, color = Color.White)
                        Spacer(modifier = Modifier.size(13.dp))
                        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                            Button(
                                onClick = {navController.navigate(ROUTE_ADMIN_VIEW_LASHES)},
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                            )
                            { Text("VIEW LASHES") }
                            Button(
                                onClick = {navController.navigate(ROUTE_ADD_LASHES)  },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                            )
                            { Text("ADD LASHES") }
                        }

                    }
                }// End of eye lashes

                Card(
                    modifier = Modifier.padding(vertical = 15.dp, horizontal = 8.dp).fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(10.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(color = 0xFFB519A0))
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        /* Icon(Icons.Default.Person, contentDescription = "Add Employee", modifier = Modifier.size(40.dp), Color(0xFF004D40))
                         Spacer(modifier = Modifier.width(20.dp))*/

                        Text("OTHER SERVICES", fontSize = 20.sp, color = Color.White)
                        Spacer(modifier = Modifier.size(13.dp))
                        Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
                            Button(
                                onClick = {},
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                            )
                            { Text("VIEW SERVICES") }
                            Button(
                                onClick = {  },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                            )
                            { Text("ADD SERVICE") }
                        }

                    }
                }//Other Services

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun adminDashboardPreview( ){
    adminDashboard(rememberNavController())

}