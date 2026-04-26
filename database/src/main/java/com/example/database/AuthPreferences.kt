package com.example.database

import android.content.Context
import com.example.weatherapp.userDataStore

object UserDataStore {
    suspend fun saveUser(context: Context, login: String) {
        context.userDataStore.updateData {
            it.toBuilder().setLogin(login).build()
        }
    }

    suspend fun getUser(context: Context): String {
        return context.userDataStore.data.map { it.login }.first()
    }

    suspend fun clearUser(context: Context): UserPreferences1 {
        return context.userDataStore.updateData { it.toBuilder().clearLogin().build() }
    }
}