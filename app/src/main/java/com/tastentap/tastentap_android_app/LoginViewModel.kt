package com.tastentap.tastentap_android_app

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(context: android.content.Context) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow<Boolean>(false)
    private val appContext = context
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    init {
        // Check if credentials valid w/ server

    }

    private fun handleLogin(success: Boolean) {
        if (success) {
            _isLoggedIn.value = true
        }
        else {
            Log.d("authentication", "auth failed")
        }
    }

    fun tryLogin(user: String, pass: String){
        if (user == ""){
            // Logic to say missing field
            Log.d("authentication", "user is missing")
        }
        else if(pass == "") {
            Log.d("authentication", "password is missing")
        }
        else {
            UserModel.establish(user, pass, appContext, ::handleLogin)
        }
    }
}