package com.example.weatherapp.data

import android.content.Context
import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.data.local.WeatherEntity
import com.example.weatherapp.data.model.ForecastItem
import com.example.weatherapp.data.model.WeatherData
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.utils.isInternetAvailable

class WeatherRepository(
    private val api: WeatherApi,
    private val dao: WeatherDao,
    private val apiKey: String
) {
    suspend fun getWeather(city: String, context: Context): WeatherData {
        val hasInternet = isInternetAvailable(context)
        if (hasInternet) {
            try {
                val response = api.getForecast(apiKey, city)
                val data = mapToWeather(response)
                dao.insert(data.toEntity())
                return data

            } catch (e: Exception) {

                return getFromDb(city)

            }
        } else {
            return getFromDb(city)
        }
    }
    suspend fun getLastCity(): String? {
        return dao.getLastCity()
    }
    private suspend fun getFromDb(city: String): WeatherData {
        val cached = dao.getWeather(city)
        return cached?.let {
            WeatherData(
                city = it.city,
                temperature = it.temperature,
                condition = it.condition,
                maxTemp = it.maxTemp,
                minTemp = it.minTemp,
                feelsLike = it.feelsLike,
                humidity = it.humidity,
                windSpeed = it.windSpeed,
                forecast = emptyList(),
                pressure = it.pressure.toDouble()
            )
        } ?: throw Exception("Нет данных в БД")
    }

    private fun mapToWeather(response: WeatherResponse): WeatherData {
        val forecastList = response.forecast.forecastday.map {
            ForecastItem(
                date = it.date,
                maxTemp = it.day.maxtemp_c.toInt(),
                minTemp = it.day.mintemp_c.toInt(),
                condition = it.day.condition.text
            )
        }

        return WeatherData(
            city = response.location.name,
            temperature = response.current.temp_c.toInt(),
            condition = response.current.condition.text,
            maxTemp = response.forecast.forecastday[0].day.maxtemp_c.toInt(),
            minTemp = response.forecast.forecastday[0].day.mintemp_c.toInt(),
            feelsLike = response.current.feelslike_c.toInt(),
            humidity = response.current.humidity,
            windSpeed = response.current.wind_kph / 3.6,
            pressure = response.current.pressure_mb.toDouble(),
            forecast = forecastList
        )
    }

    private fun WeatherData.toEntity(): WeatherEntity {
        return WeatherEntity(
            city = city,
            temperature = temperature,
            condition = condition,
            maxTemp = maxTemp,
            minTemp = minTemp,
            feelsLike = feelsLike,
            humidity = humidity,
            windSpeed = windSpeed,
            pressure = pressure.toInt()
        )
    }
}

