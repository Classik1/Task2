package com.example.weatherapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.AuthRepository
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.model.WeatherData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _weather = MutableStateFlow<WeatherData?>(null)
    val weather: StateFlow<WeatherData?> = _weather
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    private val _city = MutableStateFlow("Taganrog")
    val city: StateFlow<String> = _city
    fun init(context: Context) {
        viewModelScope.launch {
            val user = authRepository.currentUser
            val startCity = user?.lastCity ?: "Taganrog"

            _city.value = startCity
            loadWeather(startCity)
        }
    }

    fun searchCity(newCity: String) {
        viewModelScope.launch {
            _city.value = newCity
            authRepository.saveCity(newCity)
            loadWeather(newCity)
        }
    }

    private fun loadWeather(city: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                _weather.value = repository.getWeather(city)
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки"
            }

            _loading.value = false
        }
    }
}