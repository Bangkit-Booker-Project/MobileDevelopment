package com.example.capstonesean.homePage

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonesean.booklayout.BookLayoutActivity
import com.example.capstonesean.data.response.BookItem
import com.example.capstonesean.databinding.BookSimpleListBinding

class MyBooksAdapter (private val bookItem: List<BookItem>): RecyclerView.Adapter<MyBooksAdapter.BookViewHolder>() {

    class BookViewHolder(var binding: BookSimpleListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding =
            BookSimpleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

//        val bookItem = bookItem.toMutableList()
//
        if (bookItem[position].userRating == 5.01.toFloat()){
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        }
//        else {
//            holder.itemView.visibility = View.VISIBLE
//            holder.itemView.layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        }

        Glide.with(holder.binding.ivBookImg.context)
            .load(bookItem[position].bookImage)
            .into(holder.binding.ivBookImg)
        holder.binding.tvTitle.text = bookItem[position].bookTitle
        holder.binding.tvAuthor.text = bookItem[position].bookAuthor
        holder.binding.tvRating.text = bookItem[position].userRating.toString()

        holder.itemView.setOnClickListener {
            val moveToDetailBook = Intent(holder.itemView.context, BookLayoutActivity::class.java)
            moveToDetailBook.putExtra(BookLayoutActivity.TITLE, bookItem[position].bookTitle)
            holder.itemView.context.startActivity(moveToDetailBook)
        }
    }

    override fun getItemCount() = bookItem.size
}