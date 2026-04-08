package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.WeatherData
import com.example.weatherapp.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _weather = MutableStateFlow<WeatherData?>(null)
    val weather: StateFlow<WeatherData?> get() = _weather
    private val _loading = MutableStateFlow(false)
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error
    val loading: StateFlow<Boolean> get() = _loading
    fun loadWeather(city: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _weather.value = repository.getWeather(city)
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки"
                _weather.value = null
            }
            _loading.value = false
        }
    }
}