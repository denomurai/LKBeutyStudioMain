package com.example.lkbeautystore.ui.theme.CustomerScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lkbeautystore.R
import com.example.lkbeautystore.navigation.ROUTE_ADMIN_LOGIN
import com.example.lkbeautystore.navigation.ROUTE_USER_REGISTER
import com.example.lkbeautystore.viewModel.CustAuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun userLogin (navController: NavController){

    var email by remember { mutableStateOf(value = "") }

    var password by remember { mutableStateOf(value = "") }

    val custauthviewnodel: CustAuthViewModel = viewModel ()

    val context = LocalContext.current

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
            BottomAppBar(containerColor = Color(0xFF28A9B5)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),

                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        "\u00A9 Lk Beauty Studio 2025",
                        color = Color.White,
                        fontSize = 16.sp,
                    )


                    TextButton(onClick = { navController.navigate(ROUTE_ADMIN_LOGIN) }) {
                        Text("Admin Login", color = Color.White)
                    }
                }
            }
        }


    ) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login Here",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Enter Email") },
                placeholder = { Text(text = "please enter Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) })
            Spacer(modifier = Modifier.height(height = 24.dp))


            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Enter your password") },
                placeholder = { Text("Please Enter your password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    custauthviewnodel.login(
                        email = email,
                        password = password,
                        navController = navController,
                        context = context
                    )
                }, modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            )
            { Text(text = "Login", color = Color.White, fontSize = 20.sp) }

            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "If not Registered?")
            Text(
                text = "Register here", modifier = Modifier.clickable {
                    navController.navigate(ROUTE_USER_REGISTER)
                },
                color = Color.Blue, fontWeight = FontWeight.Bold
            )

        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun userloginPreview(){
    //(navController = rememberNavController())

}