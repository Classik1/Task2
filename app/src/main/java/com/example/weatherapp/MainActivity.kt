package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.data.remote.RetrofitInstance
import com.example.weatherapp.data.local.DatabaseProvider
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.ui.WeatherApp
import com.example.weatherapp.utils.Constants
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = WeatherRepository(
            api = RetrofitInstance.api,
            dao = DatabaseProvider.getDatabase(this).weatherDao(),
            apiKey = Constants.API_KEY
        )

        setContent {
            val vm: WeatherViewModel = viewModel(
                factory = WeatherViewModelFactory(repository)
            )
            WeatherApp(vm)
        }
    }
}