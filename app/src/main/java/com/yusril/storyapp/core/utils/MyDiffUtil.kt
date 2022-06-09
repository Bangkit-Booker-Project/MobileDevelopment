package com.yusril.storyapp.core.utils

import androidx.recyclerview.widget.DiffUtil
import com.yusril.storyapp.core.domain.model.Story

class MyDiffUtil(
    private val oldList: List<Story>,
    private val newList: List<Story>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> false
            oldList[oldItemPosition].name != newList[newItemPosition].name -> false
            oldList[oldItemPosition].description != newList[newItemPosition].description -> false
            oldList[oldItemPosition].photoUrl != newList[newItemPosition].photoUrl -> false
            oldList[oldItemPosition].createdAt != newList[newItemPosition].createdAt -> false
            else -> true
        }
    }
}