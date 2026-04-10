//package com.example.weatherapp.utils
//
//import android.content.Context
//import androidx.datastore.preferences.core.*
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.first
//
//object UserPreferences {
//
//    private val Context.dataStore by preferencesDataStore("settings")
//
//    private val CITY = stringPreferencesKey("city")
//
//    suspend fun saveCity(context: Context, city: String) {
//        context.dataStore.edit {
//            it[CITY] = city
//        }
//    }
//
//    suspend fun getCity(context: Context): String? {
//        return context.dataStore.data.first()[CITY]
//    }
//}