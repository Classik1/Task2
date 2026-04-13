package com.example.weatherapp.utils

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

object AuthPreferences {

    private val Context.dataStore by preferencesDataStore("auth")

    private val LOGIN = stringPreferencesKey("login")

    suspend fun saveUser(context: Context, login: String) {
        context.dataStore.edit {
            it[LOGIN] = login
        }
    }

    suspend fun getUser(context: Context): String? {
        return context.dataStore.data.first()[LOGIN]
    }
}