package com.example.capstonesean.homePage

import androidx.lifecycle.ViewModel
import com.example.capstonesean.data.repository.BookRepository

class HomepageViewModel (private val repository: BookRepository) : ViewModel() {

    fun getRecommendedBooks(token: String) = repository.getRecommendedBook(token)

    fun getTopRatedBooks(genre: String) = repository.getTopRatedBook(genre)

    fun getMyBooks(token: String) = repository.getReadBooks(token)

    fun searchBooks(query: String) = repository.searchBook(query)
}