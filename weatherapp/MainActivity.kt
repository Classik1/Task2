package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.api.RetrofitInstance
import com.example.weatherapp.data.DatabaseProvider
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = WeatherRepository(
            api = RetrofitInstance.api,
            dao = DatabaseProvider.getDatabase(this).weatherDao(),
            apiKey = "d050c24954604d2889a140805260104"
        )

        setContent {
            val vm: WeatherViewModel = viewModel(
                factory = WeatherViewModelFactory(repository)
            )
            WeatherApp(vm)
        }
    }
}