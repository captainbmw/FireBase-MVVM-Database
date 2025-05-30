package com.bmw.firebase.data

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bmw.firebase.model.User
import com.bmw.firebase.navigation.ROUTE_HOME
import com.bmw.firebase.navigation.ROUTE_LOGIN
import com.bmw.firebase.navigation.ROUTE_REGISTER
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase


class AuthViewModel (var navController: NavHostController,var context: Context){
    var mAuth: FirebaseAuth

    init {
        mAuth= FirebaseAuth.getInstance()

    }
    fun signup(email:String,pass:String,confpass:String){


        if (email.isBlank() || pass.isBlank() || confpass.isBlank()){

            Toast.makeText(context,"Please email and password can't be blank",Toast.LENGTH_LONG).show()
            return
        }else if (pass != confpass){
            Toast.makeText(context,"password do not match",Toast.LENGTH_LONG).show()
            return
        }else{
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                if (it.isSuccessful){
                    val userdata= User(email,pass,mAuth.currentUser!!.uid)
                    val regeRef= FirebaseDatabase.getInstance().getReference()
                        .child("Users/"+mAuth.currentUser!!.uid)
                    regeRef.setValue(userdata).addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
                            navController.navigate(ROUTE_LOGIN)
                        }
                    }
                }else{
                    navController.navigate(ROUTE_REGISTER)
                }
            }
        }

    }
    fun login(email: String,pass: String){
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
            if (it.isSuccessful){
                Toast.makeText(context,"Successful Logged In",Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_HOME)
            }else{
                Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_LOGIN)
            }
        }
    }
    fun logout(){
        mAuth.signOut()
        navController.navigate(ROUTE_LOGIN)
    }
    fun isloggedin():Boolean{
        return mAuth.currentUser !=null
    }
}