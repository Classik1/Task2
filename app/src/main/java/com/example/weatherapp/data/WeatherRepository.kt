package com.example.weatherapp.data

import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.data.local.WeatherEntity
import com.example.weatherapp.data.model.ForecastItem
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.model.WeatherData
import kotlin.text.toInt


class WeatherRepository(
    private val api: WeatherApi,
    private val dao: WeatherDao,
    private val apiKey: String
) {
    suspend fun getWeather(city: String): WeatherData {
        val response = api.getForecast(apiKey, city)
        val forecastList = response.forecast.forecastday.map {
            ForecastItem(
                date = it.date,
                maxTemp = it.day.maxtemp_c.toInt(),
                minTemp = it.day.mintemp_c.toInt(),
                condition = it.day.condition.text
            )
        }
        return try {
            val data = WeatherData(
                city = response.location.name,
                temperature = response.current.temp_c.toInt(),
                condition = response.current.condition.text,
                maxTemp = response.forecast.forecastday[0].day.maxtemp_c.toInt(),
                minTemp = response.forecast.forecastday[0].day.mintemp_c.toInt(),
                feelsLike = response.current.feelslike_c.toInt(),
                humidity = response.current.humidity,
                windSpeed = response.current.wind_kph / 3.6,
                forecast = forecastList,
                pressure = response.current.pressure_mb.toDouble()
            )
            dao.insert(
                WeatherEntity(
                    city = data.city,
                    temperature = data.temperature,
                    condition = data.condition,
                    maxTemp = data.maxTemp,
                    minTemp = data.minTemp,
                    feelsLike = data.feelsLike,
                    humidity = data.humidity,
                    windSpeed = data.windSpeed,
                    pressure = data.pressure.toInt()
                )
            )
            data
        } catch (e: Exception) {
            dao.getWeather(city)?.let {

                WeatherData(
                    city = it.city,
                    temperature = it.temperature,
                    condition = it.condition,
                    maxTemp = it.maxTemp,
                    minTemp = it.minTemp,
                    feelsLike = it.feelsLike,
                    humidity = it.humidity,
                    windSpeed = it.windSpeed,
                    forecast = forecastList,
                    pressure = it.pressure.toDouble()
                )
            } ?: throw e
        }
    }
}