package com.example.capstonesean.login

import androidx.lifecycle.ViewModel
import com.example.capstonesean.data.repository.UserRepository

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    fun userLogin(email: String, password: String) = repository.userLogin(email, password)
}