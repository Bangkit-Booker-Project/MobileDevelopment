package com.example.capstonesean.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonesean.booklayout.BookLayoutActivity
import com.example.capstonesean.data.response.BookItem
import com.example.capstonesean.databinding.BookSimpleListBinding

class BookSimpleAdapter (private val bookItem: List<BookItem>): RecyclerView.Adapter<BookSimpleAdapter.BookViewHolder>() {

    class BookViewHolder(var binding: BookSimpleListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding =
            BookSimpleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

        Glide.with(holder.binding.ivBookImg.context)
            .load(bookItem[position].bookImage)
            .into(holder.binding.ivBookImg)
        holder.binding.tvTitle.text = bookItem[position].bookTitle
        holder.binding.tvAuthor.text = bookItem[position].bookAuthor
        holder.binding.tvRating.text = bookItem[position].bookRating.toString()

        holder.itemView.setOnClickListener {
            val moveToDetailBook = Intent(holder.itemView.context, BookLayoutActivity::class.java)
            moveToDetailBook.putExtra(BookLayoutActivity.TITLE, bookItem[position].bookTitle)
            holder.itemView.context.startActivity(moveToDetailBook)
        }

    }

    override fun getItemCount() = bookItem.size
}