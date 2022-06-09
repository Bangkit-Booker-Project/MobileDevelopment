package com.yusril.storyapp.core.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "stories")
data class StoryItem(
    @PrimaryKey
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
