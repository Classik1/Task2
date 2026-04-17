package com.example.auth

import android.content.Context

class AuthRepository(private val context: Context) {

    var currentUser: String? = null
        private set

    suspend fun login(login: String, password: String, remember: Boolean): Boolean {
        currentUser = login

        if (remember) {
            UserDataStore.saveUser(context, login)
        }

        return true
    }

    suspend fun getSavedUser(): String? {
        val user = UserDataStore.getUser(context)
        currentUser = user
        return user
    }

    suspend fun restoreUser(login: String) {
        currentUser = login
    }
    suspend fun clearSession() {
        currentUser = null
        UserDataStore.clearUser(context)
    }
}