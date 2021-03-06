package com.example.capstonesean.data.response

import com.google.gson.annotations.SerializedName

data class BookArrayResponse(

	@field:SerializedName("result")
	val result: List<BookItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class BookArrayResponse2(

	@field:SerializedName("ratedBook")
	val ratedBook: List<BookItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class BookDetailResponse(

	@field:SerializedName("result")
	val result: BookItem,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class BookItem(

	@field:SerializedName("yearOfPublication")
	val yearOfPublication: String,

	@field:SerializedName("bookImage")
	val bookImage: String,

	@field:SerializedName("bookGenre1")
	val bookGenre1: String,

	@field:SerializedName("bookRating")
	val bookRating: Float,

	@field:SerializedName("bookGenre3")
	val bookGenre3: String,

	@field:SerializedName("bookGenres")
	val bookGenres: String,

	@field:SerializedName("bookGenre2")
	val bookGenre2: String,

	@field:SerializedName("Publisher")
	val publisher: String,

	@field:SerializedName("bookAuthor")
	val bookAuthor: String,

	@field:SerializedName("ratingCount")
	val ratingCount: Int,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("bookDesc")
	val bookDesc: String,

	@field:SerializedName("bookPages")
	val bookPages: String,

	@field:SerializedName("ISBN")
	val iSBN: String,

	@field:SerializedName("userRating")
	val userRating: Float,

	@field:SerializedName("bookTitle")
	val bookTitle: String
)

data class SimpleResponse(
	val error: Boolean,
	val message: String
)

data class LoginResponse(

	@field:SerializedName("result")
	val result: LoginResult,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class LoginResult(

	@field:SerializedName("uid")
	val uid: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("username")
	val username: String
)