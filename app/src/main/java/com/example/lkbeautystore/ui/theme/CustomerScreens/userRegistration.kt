package com.example.lkbeautystore.ui.theme.CustomerScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lkbeautystore.R
import com.example.lkbeautystore.navigation.ROUTE_USER_LOGIN
import com.example.lkbeautystore.viewModel.CustAuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun userRegistration (navController: NavController) {
    var firstname by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }

    val genderOptions= listOf( "Male", "Female","Others")
    var expanded by remember { mutableStateOf(false) }

    val custauthviewmodel: CustAuthViewModel= viewModel()
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

                /*actions = {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Log out", modifier = Modifier.size(40.dp), Color.White)
                }*/)
        },
        bottomBar = {
            BottomAppBar (containerColor = Color(0xff28a9b5)) {
                Text(
                    "\u00A9 Lk Beauty Studio 2025",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }


    ){innerPadding ->
        val scrollState = rememberScrollState()
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {

            Column (modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(18.dp))
                OutlinedTextField(
                    value = firstname,
                    onValueChange = { firstname = it },
                    label = { Text("Enter your FirstName") },
                    placeholder = { Text("Enter FirstName") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "first name") })
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = lastname,
                    onValueChange = { lastname = it },
                    label = { Text("Enter your LastName") },
                    placeholder = { Text("Enter LastName") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Last name") })
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Enter Email") },
                    placeholder = { Text("Enter E-Mail") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") })
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = phoneNo,
                    onValueChange = { phoneNo = it },
                    label = { Text("Enter Phone Number") },
                    placeholder = { Text("ie 07...") },
                    leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "phone No") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(12.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    //modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
                ) {
                    OutlinedTextField(
                        value = gender,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Gender") },
                        placeholder = { Text("Select gender") },
                        shape = RoundedCornerShape(16.dp),

                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier.menuAnchor()

                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        genderOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    gender = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Enter your Password") },
                    placeholder = { Text("Enter Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "password") })
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = confirmpassword,
                    onValueChange = { confirmpassword = it },
                    label = { Text("Confirm Password") },
                    placeholder = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "password") })
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        custauthviewmodel.signup(
                            firstname = firstname,
                            lastname = lastname,
                            email = email,
                            phoneNo = phoneNo,
                            gender = gender,
                            password = password,
                            confirmpassword = confirmpassword,
                            navController = navController,
                            context = context
                        )
                    }, modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                )
                { Text(text = "Register", color = Color.White, fontSize = 25.sp) }
                Text(text = "Already registered?")
                Text(
                    text = "Login here", modifier = Modifier.clickable {
                        navController.navigate(
                            ROUTE_USER_LOGIN
                        )
                    },
                    color = Color.Blue, fontWeight = FontWeight.Bold
                )
            }

        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun userRegistrationPreview( ){
    userRegistration(rememberNavController())
}