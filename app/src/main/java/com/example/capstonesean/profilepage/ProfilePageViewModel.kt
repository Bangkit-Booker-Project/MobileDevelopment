package com.example.capstonesean.profilepage

import androidx.lifecycle.ViewModel
import com.example.capstonesean.data.repository.UserRepository

class ProfilePageViewModel(private val repository: UserRepository) : ViewModel() {

    fun userDetail() = repository.userDetail()
}