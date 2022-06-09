package com.yusril.storyapp.core.data.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(stories: List<StoryItem>)

    @Query("SELECT * FROM stories")
    fun getAllStories(): PagingSource<Int, StoryItem>

    @Query("DELETE FROM stories")
    suspend fun deleteStories()
}