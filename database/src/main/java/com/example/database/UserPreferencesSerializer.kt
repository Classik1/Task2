package com.example.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.serialization.Serializer
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer: Serializer<UserPreferences1> {
    override val defaultValue:
            UserPreferences1 = UserPreferences1.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): UserPreferences1 {
        return try {
            UserPreferences1.parseFrom(input)
        } catch (exception: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserPreferences1, output: OutputStream){
        t.writeTo(output)
    }
}

val Context.userDataStore: DataStore<UserPreferences1> by dataStore(
    fileName = "user.pb",
    serializer = UserPreferencesSerializer
)
