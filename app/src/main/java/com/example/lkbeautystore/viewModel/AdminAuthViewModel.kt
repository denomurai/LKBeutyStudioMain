package com.example.lkbeautystore.viewModel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.lkbeautystore.navigation.ROUTE_ADMIN_DASHBOARD

class AdminAuthViewModel: ViewModel(){
    fun login(email: String,password: String, navController: NavController, context: android.content.Context){
        if(email.isBlank() || password.isBlank()){
            Toast.makeText(context,"Email and Password required", Toast.LENGTH_LONG).show()
        }
        val adminEmail = "admin@lk.com"
        val adminPassword = "admin"
        if (email == adminEmail && password == adminPassword) {
            Toast.makeText(context, "Admin login successful", Toast.LENGTH_LONG).show()
            navController.navigate(ROUTE_ADMIN_DASHBOARD) {
                popUpTo(0)
            }
        } else {
            Toast.makeText(context, "Invalid admin credentials", Toast.LENGTH_LONG).show()
        }
    }// admin login function
}
