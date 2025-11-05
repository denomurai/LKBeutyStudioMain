package com.example.lkbeautystore.viewModel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.lkbeautystore.Models.CustomerModel
import com.example.lkbeautystore.navigation.ROUTE_DASHBOARD
import com.example.lkbeautystore.navigation.ROUTE_USER_LOGIN
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CustAuthViewModel: ViewModel() {
    private val custauth: FirebaseAuth = FirebaseAuth.getInstance()
    fun signup(firstname: String, lastname: String, email: String, phoneNo: String, gender:String, password: String, confirmpassword: String, navController: NavController, context: android.content.Context){
        if (firstname.isBlank() || lastname.isBlank() || email.isBlank()||phoneNo.isBlank()||gender.isBlank() || password.isBlank() || confirmpassword.isBlank()){
            Toast.makeText(context,"Please fill all the fields", Toast.LENGTH_LONG).show()
            return
        }
        if(password != confirmpassword){
            Toast.makeText(context,"Password do not match", Toast.LENGTH_LONG).show()
        return
        }
        custauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                task ->
            if(task.isSuccessful){
                val userId = custauth.currentUser?.uid?:""
                val customer = CustomerModel( firstname = firstname, lastname = lastname, email = email, phoneNo = phoneNo,gender=gender, userId = userId)

                saveUserToDatabase(customer,navController,context)
            
            } else{
                Toast.makeText(context,"Registration failed", Toast.LENGTH_LONG).show()
            }
        }

    }//end of signup function FirebaseDatabase

    private fun saveUserToDatabase(customer: CustomerModel, navController: NavController,context: android.content.Context){
        val dbRef = FirebaseDatabase.getInstance().getReference("Customer/${customer.userId}")
        dbRef.setValue(customer).addOnCompleteListener {
                task ->
            if (task.isSuccessful){
                Toast.makeText(context,"User Registered Successful", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_USER_LOGIN){
                    popUpTo(0)
                }
            }else{
                Toast.makeText(context,"Failed to save User", Toast.LENGTH_LONG).show()
            }
        }

    }//end of save user to DB function

    fun login(email: String,password: String, navController: NavController, context: android.content.Context){
        if(email.isBlank() || password.isBlank()){
            Toast.makeText(context,"Email and Password required", Toast.LENGTH_LONG).show()
        }
        custauth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                task ->
            if(task.isSuccessful){
                Toast.makeText(context,"Login successful", Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_DASHBOARD){
                    popUpTo(0)
                }
            }else{
                Toast.makeText(context,"Login Failed",Toast.LENGTH_LONG).show()
            }
        }
    }// end of customer login function
}