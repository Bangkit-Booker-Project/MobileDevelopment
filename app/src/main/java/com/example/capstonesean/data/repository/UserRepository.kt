package com.example.capstonesean.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.capstonesean.data.Fetch
import com.example.capstonesean.data.response.LoginResult
import com.example.capstonesean.data.retrofit.ApiService

class UserRepository(private val apiService: ApiService) {

    fun userLogin(email: String, password: String): LiveData<Fetch<LoginResult>> = liveData {
        emit(Fetch.Loading)
        try {
            val response = apiService.login(email, password)
            if(!response.error) {
                emit(Fetch.Success(response.result))
            } else {
                emit(Fetch.NotFound)
            }
        } catch (e: Exception){
            Log.d("Repository", "userLogin: ${e.message.toString()}")
            emit(Fetch.Error(e.message.toString()))
        }
    }

    fun userRegister(username: String, email: String, password: String): LiveData<Fetch<String>> = liveData {
        emit(Fetch.Loading)
        try {
            val response = apiService.register(username, email, password)
            if(!response.error) {
                emit(Fetch.Success(response.message))
            } else {
                emit(Fetch.NotFound)
            }
        } catch (e: Exception){
            Log.d("Repository", "userRegister: ${e.message.toString()}")
            emit(Fetch.Error(e.message.toString()))
        }
    }

    fun changePassword(password: String, token: String): LiveData<Fetch<String>> = liveData {
        emit(Fetch.Loading)
        try {
            val response = apiService.resetPassword(password, token)
            if(!response.error) {
                emit(Fetch.Success(response.message))
            } else {
                emit(Fetch.NotFound)
            }
        } catch (e: Exception){
            Log.d("Repository", "userRegister: ${e.message.toString()}")
            emit(Fetch.Error(e.message.toString()))
        }
    }

    fun userDetail(){}

    fun getBookList(){}

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService): UserRepository =
            instance ?: synchronized(this){
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}