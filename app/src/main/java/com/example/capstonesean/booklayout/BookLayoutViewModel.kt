package com.example.capstonesean.booklayout

import androidx.lifecycle.ViewModel
import com.example.capstonesean.data.repository.BookRepository

class BookLayoutViewModel (private val repository: BookRepository) : ViewModel() {

    fun getBookDetail(bookName: String) = repository.getInfoBook(bookName)

    fun getSimilarBooks(bookName: String) = repository.getSimilarBook(bookName)
}