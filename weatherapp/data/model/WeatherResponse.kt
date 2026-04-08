package com.example.weatherapp.data.model

data class WeatherResponse(
    val location: Location,
    val current: Current,
    val forecast: Forecast
)

data class Location(val name: String)
data class Current(
    val temp_c: Double,
    val condition: Condition,
    val feelslike_c: Double,
    val humidity: Int,
    val wind_kph: Double,
    val pressure_mb: Int
)
data class Forecast(val forecastday: List<ForecastDay>)
data class ForecastDay(val date: String, val day: Day)
data class Day(val maxtemp_c: Double, val mintemp_c: Double, val condition: Condition)
data class Condition(val text: String)