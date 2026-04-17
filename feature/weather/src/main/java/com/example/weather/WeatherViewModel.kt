package com.example.weather

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.AuthRepository
import com.example.repository.WeatherData
import com.example.repository.WeatherRepository
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
            val lastCity = repository.getLastCity() ?: "Berlin"
            _city.value = lastCity
            loadWeather(lastCity, context)
        }
    }

    fun searchCity(newCity: String, context: Context) {
        viewModelScope.launch {
            _city.value = newCity
            loadWeather(newCity, context)
        }
    }

    fun loadWeather(city: String, context: Context) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _weather.value = repository.getWeather(city, context)
            } catch (_: Exception) {
                _error.value = "Нет данных"
            }
            _loading.value = false
        }
    }

    fun logout(onLogoutFinished: () -> Unit) {
        viewModelScope.launch {
            authRepository.clearSession()
            onLogoutFinished()
        }
    }
}