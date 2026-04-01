package com.example.weatherapp.api

import com.example.weatherapp.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int = 7
    ): WeatherResponse
}