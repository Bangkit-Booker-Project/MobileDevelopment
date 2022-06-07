package com.example.capstonesean.data.retrofit

import com.example.capstonesean.data.response.BookArrayResponse
import com.example.capstonesean.data.response.BookDetailResponse
import com.example.capstonesean.data.response.BookItem
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("book/{bookName}")
    suspend fun getBookInfo(
        @Path("bookName") bookName:String
    ): BookDetailResponse

    @GET("similiarBooks/{bookName}")
    suspend fun getSimilarBooks(
        @Path("bookName") bookName:String
    ): BookArrayResponse

    @GET("topRatings/{genreName}")
    suspend fun getTopGenre(
        @Path("genreName") genreName:String
    ): BookArrayResponse
}