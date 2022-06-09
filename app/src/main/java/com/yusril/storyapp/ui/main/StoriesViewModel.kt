package com.yusril.storyapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.yusril.storyapp.core.data.local.room.StoryItem
import com.yusril.storyapp.core.domain.usecase.StoryUseCase

class StoriesViewModel(private val storyUseCase: StoryUseCase): ViewModel() {
    fun getStories(token: String) : LiveData<PagingData<StoryItem>> = storyUseCase.getStoriesWithPaging(token).cachedIn(viewModelScope)
}