package com.example.auth

import android.content.Context

interface UsageStorage {
    suspend fun saveUser(context: Context, login: String)
    suspend fun getUser(context: Context)
    suspend fun clearUser(context: Context)
}