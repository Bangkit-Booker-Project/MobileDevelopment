package com.example.capstonesean.booklayout

import androidx.lifecycle.ViewModel
import com.example.capstonesean.data.repository.BookRepository

class BookLayoutViewModel (private val repository: BookRepository) : ViewModel() {

    fun getBookDetail(bookName: String) = repository.getInfoBook(bookName)

    fun getSimilarBooks(bookName: String) = repository.getSimilarBook(bookName)

    fun updateMyBooks(ISBN: String, rating: Float, token:String) = repository.updateMyBooks(ISBN, rating, token)

    fun getMyBooks(token: String) = repository.getReadBooks(token)
}