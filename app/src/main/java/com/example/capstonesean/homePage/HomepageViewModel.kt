package com.example.capstonesean.homePage

import androidx.lifecycle.ViewModel
import com.example.capstonesean.data.repository.BookRepository

class HomepageViewModel (private val repository: BookRepository) : ViewModel() {

    fun getRecommendedBooks() = repository.getRecommendedBook()

    fun getTopRatedBooks(genre: String) = repository.getTopRatedBook(genre)

    fun getMyBooks() = repository.getMyBook()
}