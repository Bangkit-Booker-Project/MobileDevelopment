package com.yusril.storyapp.core.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.*
import com.yusril.storyapp.core.data.local.UserPreferences
import com.yusril.storyapp.core.data.local.room.StoryDatabase
import com.yusril.storyapp.core.data.local.room.StoryItem
import com.yusril.storyapp.core.data.remote.ApiService
import com.yusril.storyapp.core.data.remote.response.LoginResponse
import com.yusril.storyapp.core.data.remote.response.ResultResponse
import com.yusril.storyapp.core.data.remote.response.StoriesResponse
import com.yusril.storyapp.core.domain.model.Story
import com.yusril.storyapp.core.domain.model.User
import com.yusril.storyapp.core.domain.repository.IRepository
import com.yusril.storyapp.core.utils.DataMapper
import com.yusril.storyapp.core.vo.Resource
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DataRepository private constructor(
    private val database: StoryDatabase,
    private val apiService: ApiService,
    private val preferences: UserPreferences
) : IRepository {
    override fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Resource<ResultResponse>> {
        val registerResult = MutableLiveData<Resource<ResultResponse>>()
        registerResult.value = Resource.loading()
        apiService.register(name, email, password).enqueue(object : Callback<ResultResponse>
        {
            override fun onResponse(
                call: Call<ResultResponse>,
                response: Response<ResultResponse>
            ) {
                if (response.isSuccessful) {
                    registerResult.value = Resource.success(response.body())
                } else {
                    val errorMsg = JSONObject(response.errorBody()?.string()!!)
                    registerResult.value = Resource.error(errorMsg.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                registerResult.value = Resource.error(t.message)
            }

        })
        return registerResult
    }

    override fun login(email: String, password: String): LiveData<Resource<User>> {
        val loginResult = MutableLiveData<Resource<User>>()
        loginResult.value = Resource.loading()
        apiService.login(email, password).enqueue(object : Callback<LoginResponse>
        {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    loginResult.value = Resource.success(response.body()?.loginResult?.let {
                        DataMapper.mapLoginResultToUser(
                            it
                        )
                    })
                } else {
                    val errorMsg = JSONObject(response.errorBody()?.string()!!)
                    loginResult.value = Resource.error(errorMsg.getString("message"))
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResult.value = Resource.error(t.message)
            }

        })
        return loginResult
    }

    override fun getCurrentUser(): LiveData<User> = preferences.getCurrentUser().asLiveData()
    override suspend fun setNewUser(user: User) = preferences.setNewUser(user)
    override suspend fun deleteUser() = preferences.deleteUser()

    override fun getOnBoardingKey(): LiveData<Boolean> = preferences.getOnBoardingKey().asLiveData()
    override suspend fun setOnBoardingKey(state: Boolean) = preferences.setOnBoardingKey(state)

    override fun getStories(token: String): LiveData<Resource<List<Story>>> {
        val storiesResult = MutableLiveData<Resource<List<Story>>>()
        storiesResult.value = Resource.loading()
        apiService.getStories(token).enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                if (response.isSuccessful) {
                    storiesResult.value = Resource.success(response.body()?.stories?.let {
                        DataMapper.mapStoryResponseToStory(it)
                    })
                } else {
                    val errorMsg = JSONObject(response.errorBody()?.string()!!)
                    storiesResult.value = Resource.error(errorMsg.getString("message"))
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                storiesResult.value = Resource.error(t.message)
            }

        })
        return storiesResult
    }
    override fun getStoriesWithPaging(token: String): LiveData<PagingData<StoryItem>> {
        Log.d(TAG, "tes")
        Log.d(TAG, token)
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 8
            ),
            remoteMediator = StoryRemoteMediator(database, apiService, token),
            pagingSourceFactory = {
                database.storyDao().getAllStories()
            }
        ).liveData
    }

    override fun uploadStory(
        token: String,
        file: File,
        description: String
    ): LiveData<Resource<ResultResponse>> {
        val uploadResult = MutableLiveData<Resource<ResultResponse>>()
        uploadResult.value = Resource.loading()
        val desc = description.toRequestBody("text/plain".toMediaType())
        val requestImg = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imgMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImg
        )

        apiService.uploadStory(token, imgMultipart, desc).enqueue(object : Callback<ResultResponse>{
            override fun onResponse(
                call: Call<ResultResponse>,
                response: Response<ResultResponse>
            ) {
                if (response.isSuccessful) {
                    uploadResult.value = Resource.success(response.body())
                } else {
                    val errorMsg = JSONObject(response.errorBody()?.string()!!)
                    uploadResult.value = Resource.error(errorMsg.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                uploadResult.value = Resource.error(t.message)
            }
        })

        return uploadResult
    }


    companion object {
        var TAG: String = DataRepository::class.java.simpleName
        @Volatile
        private var instance : DataRepository? = null

        fun getInstance(
            database: StoryDatabase,
            apiService: ApiService,
            userPreferences: UserPreferences
        ) : DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(database, apiService, userPreferences).apply {
                    instance = this
                }
            }
    }
}