package com.yusril.storyapp.core.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yusril.storyapp.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>){

    private val userIdKey = stringPreferencesKey("user_id_key")
    private val userNameKey = stringPreferencesKey("user_name_key")
    private val tokenKey = stringPreferencesKey("token_key")
    private val isSkipOnBoarding = booleanPreferencesKey("is_skip_on_boarding")

    fun getCurrentUser() : Flow<User> {
        return dataStore.data.map {
            User(
                id = it[userIdKey] ?: "",
                name = it[userNameKey] ?: "",
                token = it[tokenKey] ?: "",
            )
        }
    }

    suspend fun setNewUser(newUser: User) {
        dataStore.edit {
            it[userIdKey] = newUser.id
            it[userNameKey] = newUser.name
            it[tokenKey] = newUser.token
        }
    }

    suspend fun deleteUser() {
        dataStore.edit {
            it.remove(userIdKey)
            it.remove(userNameKey)
            it.remove(tokenKey)
        }
    }

    fun getOnBoardingKey() : Flow<Boolean> {
        return dataStore.data.map {
            it[isSkipOnBoarding] ?: false
        }
    }

    suspend fun setOnBoardingKey(state: Boolean) {
        dataStore.edit {
            it[isSkipOnBoarding] = state
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}