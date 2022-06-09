package com.yusril.storyapp.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.yusril.storyapp.core.data.local.room.StoryItem
import com.yusril.storyapp.core.data.remote.response.ResultResponse
import com.yusril.storyapp.core.domain.model.Story
import com.yusril.storyapp.core.domain.model.User
import com.yusril.storyapp.core.domain.repository.IRepository
import com.yusril.storyapp.core.vo.Resource
import java.io.File

class StoryInteractor(private val repository: IRepository) : StoryUseCase {
    override fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Resource<ResultResponse>> = repository.register(name, email, password)

    override fun login(email: String, password: String): LiveData<Resource<User>> = repository.login(email, password)

    override fun getCurrentUser(): LiveData<User> = repository.getCurrentUser()
    override suspend fun setNewUser(user: User) = repository.setNewUser(user)
    override suspend fun deleteUser() = repository.deleteUser()

    override fun getOnBoardingKey() : LiveData<Boolean> = repository.getOnBoardingKey()
    override suspend fun setOnBoardingKey(state: Boolean) = repository.setOnBoardingKey(state)

    override fun getStories(token: String): LiveData<Resource<List<Story>>> = repository.getStories(token)
    override fun getStoriesWithPaging(token: String): LiveData<PagingData<StoryItem>> = repository.getStoriesWithPaging(token)

    override fun uploadStory(
        token: String,
        file: File,
        description: String
    ): LiveData<Resource<ResultResponse>> = repository.uploadStory(token, file, description)
}