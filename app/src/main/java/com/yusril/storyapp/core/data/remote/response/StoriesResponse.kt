package com.yusril.storyapp.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class StoriesResponse(
    @SerializedName("error")
    var error : Boolean,

    @SerializedName("message")
    var message : String,


    @SerializedName("listStory")
    var stories : List<StoryResponse>,
)

data class StoryResponse(
    @SerializedName("id")
    var id : String,

    @SerializedName("name")
    var name : String,

    @SerializedName("description")
    var description : String,

    @SerializedName("photoUrl")
    var photoUrl : String,

    @SerializedName("createdAt")
    var createdAt : String,
)
