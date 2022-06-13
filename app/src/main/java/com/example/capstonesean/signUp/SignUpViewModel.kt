package com.example.capstonesean.signUp

import androidx.lifecycle.ViewModel
import com.example.capstonesean.data.repository.UserRepository

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {

    fun userRegister(username: String, email: String, password: String) = repository.userRegister(username, email, password)
    fun userLogin(email: String, password: String) = repository.userLogin(email, password)
}