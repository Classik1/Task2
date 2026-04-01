package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.WeatherEntity
import com.example.weatherapp.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _weather = MutableStateFlow<WeatherEntity?>(null)
    val weather: StateFlow<WeatherEntity?> get() = _weather
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading
    fun loadWeather(city: String) {
        viewModelScope.launch {
            _loading.value = true
            _weather.value = repository.getWeather(city)
            _loading.value = false
        }
    }
}