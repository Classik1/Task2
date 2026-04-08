package com.example.weatherapp.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    fun getDatabase(context: Context): WeatherDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            WeatherDatabase::class.java,
            "weather_db"
        ).build()
    }
}