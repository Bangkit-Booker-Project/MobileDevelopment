package com.yusril.storyapp.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResultResponse(
    @SerializedName("error")
    var error : Boolean,

    @SerializedName("message")
    var message : String
)
