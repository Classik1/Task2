package com.example.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: WeatherDatabase? = null
    private val lock = Any()
    fun getDatabase(context: Context): WeatherDatabase {
        return INSTANCE ?: synchronized(lock) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                WeatherDatabase::class.java,
                "weather_db"
            )
                .fallbackToDestructiveMigration(true)
                .build()
                .also { INSTANCE = it }
        }
    }
}