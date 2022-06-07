package com.example.capstonesean.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstonesean.booklayout.BookLayoutViewModel
import com.example.capstonesean.data.repository.BookRepository
import com.example.capstonesean.di.Injection
import com.example.capstonesean.homePage.HomepageViewModel
import com.example.capstonesean.login.LoginViewModel
import com.example.capstonesean.signUp.SignUpViewModel

class BookModelFactory private constructor(private val repository: BookRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomepageViewModel::class.java) -> {
                HomepageViewModel(repository) as T
            }
            modelClass.isAssignableFrom(BookLayoutViewModel::class.java) -> {
                BookLayoutViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: BookModelFactory? = null
        fun getInstance(): BookModelFactory =
            instance ?: synchronized(this) {
                instance ?: BookModelFactory(Injection.provideBookRepository())
            }.also { instance = it }
    }
}