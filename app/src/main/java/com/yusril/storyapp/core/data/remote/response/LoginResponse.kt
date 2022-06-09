package com.yusril.storyapp.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("error")
    var error : Boolean,

    @SerializedName("message")
    var message : String,

    @SerializedName("loginResult")
    var loginResult : LoginResult
)

data class LoginResult(
    @SerializedName("userId")
    var userId : String,

    @SerializedName("name")
    var name : String,

    @SerializedName("token")
    var token : String
)
