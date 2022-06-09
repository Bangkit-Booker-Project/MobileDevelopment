package com.yusril.storyapp.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yusril.storyapp.R
import com.yusril.storyapp.core.data.local.room.StoryItem
import com.yusril.storyapp.core.domain.model.Story
import com.yusril.storyapp.core.utils.DataMapper.mapStoryItemToStory
import com.yusril.storyapp.core.utils.dateFormatter
import com.yusril.storyapp.databinding.ItemStoryBinding

class StoriesAdapter: PagingDataAdapter<StoryItem, StoriesAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class MyViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: StoryItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .placeholder(R.drawable.image_placeholder)
                    .into(storyImage)
                storyUserName.text = story.name
                storyDate.text = dateFormatter(story.createdAt)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
            holder.itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(
                    mapStoryItemToStory(data)
                )
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(story: Story)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryItem>() {
            override fun areItemsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}