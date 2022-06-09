package com.yusril.storyapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusril.storyapp.core.domain.model.User
import com.yusril.storyapp.core.domain.usecase.StoryUseCase
import kotlinx.coroutines.launch

class AuthViewModel(private val storyUseCase: StoryUseCase): ViewModel() {
    fun register(name: String, email: String, password: String) = storyUseCase.register(name, email, password)
    fun login(email: String, password: String) = storyUseCase.login(email, password)

    fun getCurrentUser() = storyUseCase.getCurrentUser()
    fun setNewUser(user: User) {
        viewModelScope.launch {
            storyUseCase.setNewUser(user)
        }
    }
    fun deleteUser() {
        viewModelScope.launch {
            storyUseCase.deleteUser()
        }
    }

    fun getOnBoardingKey() = storyUseCase.getOnBoardingKey()
    fun setOnBoardingKey(state: Boolean) {
        viewModelScope.launch {
            storyUseCase.setOnBoardingKey(state)
        }
    }
}