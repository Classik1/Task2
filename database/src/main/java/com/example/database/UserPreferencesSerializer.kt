package com.example.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer: Serializer<UserPref> {
    override val defaultValue:
            UserPref = UserPref.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): UserPref {
        return try {
            UserPref.parseFrom(input)
        } catch (exception: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserPref, output: OutputStream){
        t.writeTo(output)
    }
}

val Context.userDataStore: DataStore<UserPref> by dataStore(
    fileName = "user.pb",
    serializer = UserPreferencesSerializer
)