package com.example.capstonesean.homePage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonesean.data.response.BookItem
import com.example.capstonesean.databinding.BookVerticalListBinding

class BookListAdapter(private val bookItem: List<BookItem>): RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    private lateinit var onItemClickCallback : OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class BookViewHolder(var binding: BookVerticalListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = BookVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        Glide.with(holder.binding.bookImage3.context)
            .load(bookItem[position].bookImage)
            .into(holder.binding.bookImage3)
        holder.binding.bookTitle3.text = bookItem[position].bookTitle
        holder.binding.bookAuthor3.text = bookItem[position].bookAuthor
        holder.binding.bookRating3.text = bookItem[position].bookRating.toString()

        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(bookItem[holder.adapterPosition])}
    }

    override fun getItemCount() = bookItem.size

    interface OnItemClickCallback {
        fun onItemClicked(data: BookItem)
    }
}