package com.example.capstonesean.booklayout

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonesean.data.response.BookItem
import com.example.capstonesean.databinding.BookHorizontalListBinding
import com.example.capstonesean.databinding.BookVerticalListBinding
import com.example.capstonesean.homePage.BookListAdapter

class BookSimilarAdapter (private val bookItem: List<BookItem>): RecyclerView.Adapter<BookSimilarAdapter.BookViewHolder>() {

    class BookViewHolder(var binding: BookHorizontalListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding =
            BookHorizontalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

        Glide.with(holder.binding.ivBookPicture.context)
            .load(bookItem[position].bookImage)
            .into(holder.binding.ivBookPicture)

        holder.itemView.setOnClickListener {
            val moveToDetailBook = Intent(holder.itemView.context, BookLayoutActivity::class.java)
            moveToDetailBook.putExtra(BookLayoutActivity.TITLE, bookItem[position].bookTitle)
            holder.itemView.context.startActivity(moveToDetailBook)
        }

    }

    override fun getItemCount() = bookItem.size

}