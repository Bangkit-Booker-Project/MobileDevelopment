package com.yusril.storyapp.core.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yusril.storyapp.core.data.local.UserPreferences
import com.yusril.storyapp.core.di.Injection
import com.yusril.storyapp.core.domain.usecase.StoryUseCase
import com.yusril.storyapp.ui.auth.AuthViewModel
import com.yusril.storyapp.ui.createstory.UploadStoryViewModel
import com.yusril.storyapp.ui.main.StoriesViewModel

class ViewModelFactory private constructor(
    private val storyUseCase: StoryUseCase
    ) : ViewModelProvider.NewInstanceFactory()
{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(storyUseCase) as T
            modelClass.isAssignableFrom(StoriesViewModel::class.java) -> StoriesViewModel(storyUseCase) as T
            modelClass.isAssignableFrom(UploadStoryViewModel::class.java) -> UploadStoryViewModel(storyUseCase) as T
            else -> throw Throwable("Unknown viewModel class : " +modelClass.name)
        }


    companion object {
        @Volatile
        private var instance : ViewModelFactory? = null

        fun getInstance(
            context: Context,
            references: UserPreferences
        ) : ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideUseCase(context, references))
            }
    }
}