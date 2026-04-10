package com.example.weatherapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repo: AuthRepository) : ViewModel() {

    var isAuthorized = mutableStateOf(false)
        private set

    fun checkAuth() {
        viewModelScope.launch {
            isAuthorized.value = repo.getSavedUser() != null
        }
    }
    fun login(login: String, password: String, remember: Boolean) {
        viewModelScope.launch {
            isAuthorized.value = repo.login(login, password, remember)
        }
    }
}