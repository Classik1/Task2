package com.example.weatherapp.data.model

data class ForecastItem(
    val date: String,
    val maxTemp: Int,
    val minTemp: Int,
    val condition: String
)