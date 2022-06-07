package com.example.capstonesean.signUp

import androidx.lifecycle.ViewModel
import com.example.capstonesean.data.repository.UserRepository

class SignUpViewModel(private val repository: UserRepository) : ViewModel() {

    fun userRegister() = repository.userRegister()
}