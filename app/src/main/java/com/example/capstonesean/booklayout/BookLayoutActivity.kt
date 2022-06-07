package com.example.capstonesean.booklayout

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.capstonesean.data.Fetch
import com.example.capstonesean.data.response.BookItem
import com.example.capstonesean.databinding.ActivityBookLayoutBinding
import com.example.capstonesean.viewmodelfactory.BookModelFactory

class BookLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookLayoutBinding

    val viewModel: BookLayoutViewModel by viewModels {
        BookModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBookDetail()
        getSimilarBooks()
    }

    private fun getBookDetail(){
        val title = intent.getStringExtra(TITLE)
        if (title != null) {
            viewModel.getBookDetail(title).observe(this){ result ->
                if (result != null) {
                    when (result) {
                        is Fetch.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Fetch.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val data = result.data
                            setView(data)
                        }
                        is Fetch.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                result.error,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun setView(bookItem: BookItem){

        Glide.with(binding.ivBookImage)
            .load(bookItem.bookImage)
            .into(binding.ivBookImage)
        binding.tvBookTitle.text = bookItem.bookTitle
        binding.tvBookAuthor.text = bookItem.bookAuthor
        binding.tvBookRating.text = bookItem.bookRating.toString()
        binding.tvBookDesciption.text = bookItem.bookDesc
        binding.tvTextGenre.text = bookItem.bookGenre1 + ", " + bookItem.bookGenre2 + ", " + bookItem.bookGenre3
        binding.tvBookPage.text = bookItem.bookPages
        binding.buttonBookLink.setOnClickListener {
            val browserDirect = Intent(Intent.ACTION_VIEW, Uri.parse(bookItem.url))
            startActivity(browserDirect)
        }
    }

    private fun getSimilarBooks(){
        val title = intent.getStringExtra(TITLE)
        if (title != null) {
            viewModel.getSimilarBooks(title).observe(this){ result ->
                if (result != null) {
                    when (result) {
                        is Fetch.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Fetch.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val data = result.data
                            setSimilarBooks(data)
                        }
                        is Fetch.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                result.error,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun setSimilarBooks(bookItem: List<BookItem>){
        val adapter = BookSimilarAdapter(bookItem)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvBookListHorizontal.adapter = adapter
        binding.rvBookListHorizontal.layoutManager = layoutManager
    }

    companion object {
        const val TITLE = "title"
    }
}