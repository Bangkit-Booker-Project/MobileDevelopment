package com.yusril.storyapp.core.utils

import com.yusril.storyapp.core.data.local.room.StoryItem
import com.yusril.storyapp.core.data.remote.response.LoginResult
import com.yusril.storyapp.core.data.remote.response.StoryResponse
import com.yusril.storyapp.core.domain.model.Story
import com.yusril.storyapp.core.domain.model.User

object DataMapper {
    fun mapLoginResultToUser(input: LoginResult) =
        User(
            id = input.userId,
            name = input.name,
            token = "Bearer ${input.token}"
        )

    fun mapStoryItemToStory(input: StoryItem) =
        Story(
            id = input.id,
            name = input.name,
            description = input.description,
            photoUrl = input.photoUrl,
            createdAt = input.createdAt

        )

    fun mapStoryResponseToStory(input: List<StoryResponse>) : List<Story> {
        val listStory = ArrayList<Story>()
        input.map {
            val story = Story(
                id = it.id,
                name = it.name,
                description = it.description,
                photoUrl = it.photoUrl,
                createdAt = it.createdAt
            )
            listStory.add(story)
        }
        return listStory
    }

    fun mapStoryResponseToStoryItem(input: List<StoryResponse>) : List<StoryItem> {
        val listStory = ArrayList<StoryItem>()
        input.map {
            val story = StoryItem(
                id = it.id,
                name = it.name,
                description = it.description,
                photoUrl = it.photoUrl,
                createdAt = it.createdAt
            )
            listStory.add(story)
        }
        return listStory
    }

}