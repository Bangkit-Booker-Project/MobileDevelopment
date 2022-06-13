package com.example.capstonesean.data.retrofit

import com.example.capstonesean.data.response.*
import retrofit2.http.*

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

    @GET("search/{string}")
    suspend fun bookSearch(
        @Path("string") string: String
    ): BookArrayResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SimpleResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("resetPassword")
    suspend fun resetPassword(
        @Field("password") password: String,
        @Query("token") token: String
    ): SimpleResponse

    @GET("recomendation")
    suspend fun getRecommendation(
        @Query("token") token:String
    ): BookArrayResponse

    @FormUrlEncoded
    @POST("updateReadedBook")
    suspend fun updateMyBooks(
        @Field("ISBN") ISBN: String,
        @Field("bookRating") bookRating: Float,
        @Query("token") token: String
    ): BookArrayResponse

    @GET("getReadedBooks")
    suspend fun getReadBooks(
        @Query("token") token:String
    ): BookArrayResponse

}