package com.example.capstonesean.di

import com.example.capstonesean.data.repository.BookRepository
import com.example.capstonesean.data.repository.UserRepository
import com.example.capstonesean.data.retrofit.ApiConfig

object Injection {
    fun provideUserRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }

    fun provideBookRepository(): BookRepository {
        val apiService = ApiConfig.getApiService()
        return BookRepository.getInstance(apiService)
    }
}