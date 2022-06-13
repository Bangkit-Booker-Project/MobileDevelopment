package com.example.capstonesean.search

import androidx.lifecycle.ViewModel
import com.example.capstonesean.data.repository.BookRepository
import com.example.capstonesean.data.repository.UserRepository

class SearchViewModel(private val repository: BookRepository): ViewModel() {
    fun searchBooks(query: String) = repository.searchBook(query)
}