package com.example.weatherapp.data

import com.example.weatherapp.api.WeatherApi

class WeatherRepository(
    private val api: WeatherApi,
    private val dao: WeatherDao,
    private val apiKey: String
) {
    suspend fun getWeather(city: String): WeatherEntity {
        val response = try {
            api.getForecast(apiKey, city)
        } catch (e: Exception) {
            null
        }
        val entity = response?.let {
            WeatherEntity(
                city = it.location.name,
                temperature = it.current.temp_c.toInt(),
                condition = it.current.condition.text,
                maxTemp = it.forecast.forecastday[0].day.maxtemp_c.toInt(),
                minTemp = it.forecast.forecastday[0].day.mintemp_c.toInt(),
                feelsLike = it.current.feelslike_c.toInt(),
                humidity = it.current.humidity,
                windSpeed = it.current.wind_kph / 3.6,
                pressure = it.current.pressure_mb
            )
        } ?: dao.getWeather(city)
        entity?.let { dao.insert(it) }
        return entity ?: error("Нет данных о погоде")
    }
}