package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.weatherapp.data.AuthRepository
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.local.DatabaseProvider
import com.example.weatherapp.data.remote.RetrofitInstance
import com.example.weatherapp.ui.DetailsScreen
import com.example.weatherapp.ui.LoginScreen
import com.example.weatherapp.ui.WeatherApp
import com.example.weatherapp.utils.Constants
import com.example.weatherapp.utils.UserDataStore
import com.example.weatherapp.viewmodel.AuthViewModel
import com.example.weatherapp.viewmodel.AuthViewModelFactory
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = DatabaseProvider.getDatabase(this)
        val authRepo = AuthRepository(this)
        val weatherRepo = WeatherRepository(
            RetrofitInstance.api,
            db.weatherDao(),
            Constants.API_KEY
        )

        setContent {

            val navController = rememberNavController()
            val authVM: AuthViewModel = viewModel(
                factory = AuthViewModelFactory(authRepo)
            )

            val weatherVM: WeatherViewModel = viewModel(
                factory = WeatherViewModelFactory(weatherRepo, authRepo)
            )

            LaunchedEffect(Unit) {
                val savedLogin = UserDataStore.getUser(this@MainActivity)
                authRepo.restoreUser(savedLogin)
                navController.navigate("weather") {
                    popUpTo("login") { inclusive = true }
                }
            }
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                composable("login") {
                    LoginScreen(
                        vm = authVM,
                        onSuccess = {
                            navController.navigate("weather") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    )
                }

                composable("weather") {
                    WeatherApp(
                        viewModel = weatherVM,
                        onDetailsClick = {
                            navController.navigate("details")
                        },
                        onLogout = {
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }

                composable("details") {
                    DetailsScreen(
                        vm = weatherVM,
                        navController = navController
                    )
                }
            }
        }
    }
}