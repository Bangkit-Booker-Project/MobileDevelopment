package com.yusril.storyapp.ui.createstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yusril.storyapp.core.data.remote.response.ResultResponse
import com.yusril.storyapp.core.domain.usecase.StoryUseCase
import com.yusril.storyapp.core.vo.Resource
import java.io.File

class UploadStoryViewModel(private val storyUseCase: StoryUseCase): ViewModel() {
    fun uploadStory(token: String, file: File, description: String) : LiveData<Resource<ResultResponse>> {
        return storyUseCase.uploadStory(token, file, description)
    }
}