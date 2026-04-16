package com.example.weatherapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.weatherapp.UserPreferences

import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer: Serializer<UserPreferences> {
    override val defaultValue:
            UserPreferences = UserPreferences.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): UserPreferences {
        return try {
            UserPreferences.parseFrom(input)
        } catch (exception: Exception) {
            defaultValue
        }
    }
    override suspend fun writeTo(t: UserPreferences, output: OutputStream){
        t.writeTo(output)
    }
}

val Context.userDataStore: DataStore<UserPreferences> by dataStore(
    fileName = "user.pb",
    serializer = UserPreferencesSerializer
)
