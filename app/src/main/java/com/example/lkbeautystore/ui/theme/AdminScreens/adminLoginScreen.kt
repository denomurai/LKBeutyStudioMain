package com.example.lkbeautystore.ui.theme.AdminScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.example.lkbeautystore.viewModel.AdminAuthViewModel

@Composable
fun adminLoginScreen(navController: NavController){
    var email by remember { mutableStateOf(value = "") }

    var password by remember { mutableStateOf(value = "") }

    val AdminAuthViewModel: AdminAuthViewModel= viewModel () // create a variable authViewModel
    val context= LocalContext.current

    Column (modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Text( text = "ADMIN ",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text( text = "LOGIN HERE",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Image(painter = painterResource(R.drawable.logo),
            contentDescription = "App log",
            modifier = Modifier.size(140.dp))
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(value = email,
            onValueChange = {email=it},
            label = {Text(text = "Enter Email")},
            placeholder = {Text(text = "please enter Email")},
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) })
        Spacer(modifier = Modifier.height(height = 24.dp))


        OutlinedTextField(value = password,
            onValueChange = {password=it},
            label = {Text("Enter your password")},
            placeholder = {Text("Please Enter your email")},
            leadingIcon = {Icon(Icons.Default.Lock, contentDescription = null)},
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {AdminAuthViewModel.login(email=email,password=password,navController=navController, context = context)}, modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray ))
        {Text(text = "Login", color= Color.White, fontSize = 20.sp) }


    }


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun adminloginPreview(){
    adminLoginScreen(rememberNavController())

}