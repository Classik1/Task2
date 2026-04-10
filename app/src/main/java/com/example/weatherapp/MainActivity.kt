package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.data.AuthRepository
import com.example.weatherapp.data.remote.RetrofitInstance
import com.example.weatherapp.data.local.DatabaseProvider
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.ui.LoginScreen
import com.example.weatherapp.ui.WeatherApp
import com.example.weatherapp.utils.Constants
import com.example.weatherapp.viewmodel.AuthViewModel
import com.example.weatherapp.viewmodel.AuthViewModelFactory
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = DatabaseProvider.getDatabase(this)

        val authRepo = AuthRepository(db.userDao())
        val weatherRepo = WeatherRepository(
            RetrofitInstance.api,
            db.weatherDao(),
            Constants.API_KEY
        )

        setContent {

            val authVM: AuthViewModel = viewModel(
                factory = AuthViewModelFactory(authRepo)
            )

            val weatherVM: WeatherViewModel = viewModel(
                factory = WeatherViewModelFactory(weatherRepo, authRepo)
            )

            LaunchedEffect(Unit) {
                authVM.checkAuth()
            }

            if (authVM.isAuthorized.value) {
                WeatherApp(weatherVM)
            } else {
                LoginScreen(authVM)
            }
        }
    }
}