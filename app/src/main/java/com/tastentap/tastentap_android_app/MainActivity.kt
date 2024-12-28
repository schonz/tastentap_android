package com.tastentap.tastentap_android_app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import com.tastentap.tastentap_android_app.ui.Scaffold
import com.tastentap.tastentap_android_app.ui.screens.LoginScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.tastentap.tastentap_android_app.ui.theme.TasteNTapTheme

class MainActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels() {
        LoginViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TasteNTapTheme {
                //val navController = rememberNavController()
                var redirectUI by remember { mutableStateOf("login") }
                val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()

                // Launch Behavior, check for login
                LaunchedEffect(isLoggedIn) {
                    redirectUI = if (isLoggedIn) "main" else "login"
                }

                // Swap composables based on login state
                when (redirectUI) {
                    "main" -> Scaffold()
                    else -> LoginScreen(clickLogin = loginViewModel::tryLogin)
                }
            }
        }
    }
}
