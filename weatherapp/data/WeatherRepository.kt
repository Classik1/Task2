package com.example.weatherapp.data

import com.example.weatherapp.api.WeatherApi
import com.example.weatherapp.WeatherData

class WeatherRepository(
    private val api: WeatherApi,
    private val dao: WeatherDao,
    private val apiKey: String
) {
    suspend fun getWeather(city: String): WeatherData {
        val response = try {
            api.getForecast(apiKey, city)
        } catch (e: Exception) {
            null
        }
        val data = response?.let {
            WeatherData(
                city = it.location.name,
                temperature = it.current.temp_c.toInt(),
                condition = it.current.condition.text,
                maxTemp = it.forecast.forecastday[0].day.maxtemp_c.toInt(),
                minTemp = it.forecast.forecastday[0].day.mintemp_c.toInt(),
                feelsLike = it.current.feelslike_c.toInt(),
                humidity = it.current.humidity,
                windSpeed = it.current.wind_kph / 3.6,
                pressure = it.current.pressure_mb.toDouble()
            )
        } ?: dao.getWeather(city)?.let { entity ->
            WeatherData(
                city = entity.city,
                temperature = entity.temperature,
                condition = entity.condition,
                maxTemp = entity.maxTemp,
                minTemp = entity.minTemp,
                feelsLike = entity.feelsLike,
                humidity = entity.humidity,
                windSpeed = entity.windSpeed,
                pressure = entity.pressure.toDouble()
            )
        }
        data?.let {
            dao.insert(
                WeatherEntity(
                    city = it.city,
                    temperature = it.temperature,
                    condition = it.condition,
                    maxTemp = it.maxTemp,
                    minTemp = it.minTemp,
                    feelsLike = it.feelsLike,
                    humidity = it.humidity,
                    windSpeed = it.windSpeed,
                    pressure = it.pressure.toInt()
                )
            )
        }

        return data ?: error("Нет данных о погоде")
    }
}