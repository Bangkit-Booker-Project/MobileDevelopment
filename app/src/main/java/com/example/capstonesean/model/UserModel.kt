package com.example.capstonesean.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var username: String? = null,
    var userId: String? = null,
    var token: String? = null,
    var isLogin: Boolean = false
): Parcelable