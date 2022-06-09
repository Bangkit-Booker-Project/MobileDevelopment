package com.yusril.storyapp.core.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.yusril.storyapp.core.domain.model.User

class LocalDataSource private constructor(private val preferences: UserPreferences){

    fun getCurrentUser(): LiveData<User> = preferences.getCurrentUser().asLiveData()
    suspend fun setNewUser(newUser: User) = preferences.setNewUser(newUser)
    suspend fun deleteUser() = preferences.deleteUser()

    fun getOnBoardingKey() = preferences.getOnBoardingKey().asLiveData()
    suspend fun setOnBoardingKey(state: Boolean) = preferences.setOnBoardingKey(state)

    companion object {
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(preferences: UserPreferences) : LocalDataSource {
            return instance ?: synchronized(this) {
                instance ?: LocalDataSource(preferences).also {
                    instance = it
                }
            }
        }
    }
}