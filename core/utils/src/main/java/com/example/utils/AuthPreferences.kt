package com.example.utils

import android.content.Context
import androidx.datastore.preferences.core.*
import com.example.weatherapp.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

object UserDataStore {
    suspend fun saveUser(context: Context, login: String) {
        context.userDataStore.updateData {
            it.toBuilder().setLogin(login).build()
        }
    }

    suspend fun getUser(context: Context): String {
        return context.userDataStore.data.map { it.login }.first()
    }

    suspend fun clearUser(context: Context): UserPreferences {
        return context.userDataStore.updateData { it.toBuilder().clearLogin().build() }
    }
}