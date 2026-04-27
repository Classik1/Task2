package com.example.buisness

data class WeatherData(
    val city: String,
    val temperature: Int,
    val condition: String,
    val maxTemp: Int,
    val minTemp: Int,
    val feelsLike: Int,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Double,
    val forecast: List<ForecastItem>
)