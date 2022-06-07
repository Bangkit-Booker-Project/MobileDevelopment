package com.example.capstonesean.data.repository

import com.example.capstonesean.data.retrofit.ApiService

class UserRepository(private val apiService: ApiService) {

    fun userLogin(){}

    fun userRegister(){}

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