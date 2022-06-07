package com.example.capstonesean.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstonesean.data.repository.BookRepository
import com.example.capstonesean.data.repository.UserRepository
import com.example.capstonesean.di.Injection
import com.example.capstonesean.login.LoginViewModel
import com.example.capstonesean.profilepage.ProfilePageViewModel
import com.example.capstonesean.signUp.SignUpViewModel

class UserModelFactory private constructor(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfilePageViewModel::class.java) -> {
                ProfilePageViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: UserModelFactory? = null
        fun getInstance(): UserModelFactory =
            instance ?: synchronized(this) {
                instance ?: UserModelFactory(Injection.provideUserRepository())
            }.also { instance = it }
    }
}