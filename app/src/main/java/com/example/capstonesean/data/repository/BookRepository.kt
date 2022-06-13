package com.example.capstonesean.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.capstonesean.data.Fetch
import com.example.capstonesean.data.response.BookArrayResponse
import com.example.capstonesean.data.response.BookDetailResponse
import com.example.capstonesean.data.response.BookItem
import com.example.capstonesean.data.retrofit.ApiService

class BookRepository private constructor(private val apiService: ApiService){

    fun getRecommendedBook(token: String): LiveData<Fetch<List<BookItem>>> = liveData {
        emit(Fetch.Loading)
        try {
            val response = apiService.getRecommendation(token)
            if(!response.error) {
                emit(Fetch.Success(response.result))
            } else {
                emit(Fetch.NotFound)
            }
        } catch (e: Exception){
            Log.d("Repository", "getTopRatedBook: ${e.message.toString()}")
            emit(Fetch.Error(e.message.toString()))
        }
    }

    fun getTopRatedBook(genreName: String): LiveData<Fetch<List<BookItem>>> = liveData {
        emit(Fetch.Loading)
        try {
            val response = apiService.getTopGenre(genreName)
            if(!response.error) {
                emit(Fetch.Success(response.result))
            } else {
                emit(Fetch.NotFound)
            }
        } catch (e: Exception){
            Log.d("Repository", "getTopRatedBook: ${e.message.toString()}")
            emit(Fetch.Error(e.message.toString()))
        }
    }

    fun getInfoBook(bookName: String): LiveData<Fetch<BookItem>> = liveData {
        emit(Fetch.Loading)
        try {
            val response = apiService.getBookInfo(bookName)
            if(!response.error) {
                emit(Fetch.Success(response.result))
            } else {
                emit(Fetch.NotFound)
            }
        } catch (e: Exception){
            Log.d("Repository", "getInfoBook: ${e.message.toString()}")
            emit(Fetch.Error(e.message.toString()))
        }
    }

    fun getSimilarBook(bookName: String): LiveData<Fetch<List<BookItem>>> = liveData {
        emit(Fetch.Loading)
        try {
            val response = apiService.getSimilarBooks(bookName)
            if(!response.error) {
                emit(Fetch.Success(response.result))
            } else {
                emit(Fetch.NotFound)
            }
        } catch (e: Exception){
            Log.d("Repository", "getSimilarBook: ${e.message.toString()}")
            emit(Fetch.Error(e.message.toString()))
        }
    }

    fun searchBook(query: String): LiveData<Fetch<List<BookItem>>> = liveData {
        emit(Fetch.Loading)
        try {
            val response = apiService.bookSearch(query)
            if(!response.error) {
                emit(Fetch.Success(response.result))
            } else {
                emit(Fetch.NotFound)
            }
        } catch (e: Exception){
            Log.d("Repository", "searchBook: ${e.message.toString()}")
            emit(Fetch.Error(e.message.toString()))
        }
    }

    fun updateMyBooks(ISBN: String, rating: Float, token:String): LiveData<Fetch<String>> = liveData {
        emit(Fetch.Loading)
        try {
            val response = apiService.updateMyBooks(ISBN, rating, token)
            if(!response.error) {
                emit(Fetch.Success(response.message))
            } else {
                emit(Fetch.NotFound)
            }
        } catch (e: Exception){
            Log.d("Repository", "searchBook: ${e.message.toString()}")
            emit(Fetch.Error(e.message.toString()))
        }
    }

    fun getReadBooks(token: String): LiveData<Fetch<List<BookItem>>> = liveData {
        emit(Fetch.Loading)
        try {
            val response = apiService.getReadBooks(token)
            if(!response.error) {
                emit(Fetch.Success(response.result))
            } else {
                emit(Fetch.NotFound)
            }
        } catch (e: Exception){
            Log.d("Repository", "searchBook: ${e.message.toString()}")
            emit(Fetch.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: BookRepository? = null
        fun getInstance(apiService: ApiService): BookRepository =
            instance ?: synchronized(this){
                instance ?: BookRepository(apiService)
            }.also { instance = it }
    }
}