package com.yusril.storyapp.core.di

import android.content.Context
import com.yusril.storyapp.core.data.DataRepository
import com.yusril.storyapp.core.data.local.UserPreferences
import com.yusril.storyapp.core.data.local.room.StoryDatabase
import com.yusril.storyapp.core.data.remote.ApiConfig
import com.yusril.storyapp.core.domain.repository.IRepository
import com.yusril.storyapp.core.domain.usecase.StoryInteractor
import com.yusril.storyapp.core.domain.usecase.StoryUseCase

object Injection {
    private fun provideRepository(context: Context, preferences: UserPreferences) : IRepository {
        val apiService = ApiConfig.getApiService()
        val database = StoryDatabase.getDatabase(context)

        return DataRepository.getInstance(database, apiService, preferences)
    }

    fun provideUseCase(context: Context, preferences: UserPreferences) : StoryUseCase {
        val repository = provideRepository(context, preferences)

        return StoryInteractor(repository)
    }
}