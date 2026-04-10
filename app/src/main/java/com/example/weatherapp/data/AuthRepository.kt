package com.example.weatherapp.data

import com.example.weatherapp.data.local.UserDao
import com.example.weatherapp.data.local.UserEntity

class AuthRepository(private val userDao: UserDao) {

    var currentUser: UserEntity? = null
        private set

    suspend fun login(login: String, password: String, remember: Boolean): Boolean {
        val user = userDao.login(login, password)

        currentUser = if (user != null) {
            val updated = user.copy(rememberMe = remember)
            userDao.insert(updated)
            updated
        } else {
            val newUser = UserEntity(login, password, remember)
            userDao.insert(newUser)
            newUser
        }

        return true
    }

    suspend fun getSavedUser(): UserEntity? {
        val user = userDao.getRememberedUser()
        currentUser = user
        return user
    }

    suspend fun saveCity(city: String) {
        currentUser?.let {
            userDao.updateCity(it.login, city)
            currentUser = it.copy(lastCity = city)
        }
    }
}