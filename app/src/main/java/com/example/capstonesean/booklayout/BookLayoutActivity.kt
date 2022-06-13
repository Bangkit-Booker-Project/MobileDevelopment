package com.example.capstonesean.booklayout

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.capstonesean.data.Fetch
import com.example.capstonesean.data.response.BookItem
import com.example.capstonesean.databinding.ActivityBookLayoutBinding
import com.example.capstonesean.model.UserModel
import com.example.capstonesean.model.UserPreferences
import com.example.capstonesean.viewmodelfactory.BookModelFactory

class BookLayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookLayoutBinding
    private lateinit var userModel: UserModel
    private lateinit var userPreferences: UserPreferences

    val viewModel: BookLayoutViewModel by viewModels {
        BookModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()

        getBookDetail()
        getSimilarBooks()

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun getBookDetail(){
        val title = intent.getStringExtra(TITLE)
        if (title != null) {
            viewModel.getBookDetail(title).observe(this){ result ->
                if (result != null) {
                    when (result) {
                        is Fetch.Loading -> {
                            shimmybook()
                        }
                        is Fetch.Success -> {
                            stopShimmy()
                            val data = result.data
                            setView(data)
                        }
                        is Fetch.Error -> {
                            binding.shimmerBook.visibility = View.GONE
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

    private fun getUserInfo(title: String){
        viewModel.getMyBooks(userModel.token.toString()).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Fetch.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Fetch.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data
                        setUserData(data, title)
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

    private fun setUserData(data: List<BookItem>, title: String){
        val list = data.map { it.bookTitle }
        val rate = data.map { it.userRating }

        val num = list.indexOf(title)
        if (num != -1){
            binding.tvUserRating.text = rate[num].toString()
        }
    }

    private fun shimmybook() {
        binding.shimmerBook.visibility = View.VISIBLE
        binding.tvBookTitle.visibility = View.INVISIBLE
        binding.tvBookAuthor.visibility = View.INVISIBLE
        binding.tvBookDesciption.visibility = View.INVISIBLE
        binding.tvBookGenre.visibility = View.INVISIBLE
        binding.tvBookRating.visibility = View.INVISIBLE
        binding.tvBookPage.visibility = View.INVISIBLE
        binding.tvRateThisBook.visibility = View.INVISIBLE
        binding.tvTextSimilarBooks.visibility = View.INVISIBLE
        binding.tvUserRating.visibility = View.INVISIBLE
        binding.tvTextDescription.visibility = View.INVISIBLE
        binding.tvTextGenre.visibility = View.INVISIBLE
        binding.tvTextPage.visibility = View.INVISIBLE
        binding.buttonVote.visibility = View.INVISIBLE
        binding.buttonBookLink.visibility = View.INVISIBLE
    }

    private fun stopShimmy() {
        binding.shimmerBook.visibility = View.GONE
        binding.tvBookTitle.visibility = View.VISIBLE
        binding.tvBookAuthor.visibility = View.VISIBLE
        binding.tvBookDesciption.visibility = View.VISIBLE
        binding.tvBookGenre.visibility = View.VISIBLE
        binding.tvBookRating.visibility = View.VISIBLE
        binding.tvBookPage.visibility = View.VISIBLE
        binding.tvRateThisBook.visibility = View.VISIBLE
        binding.tvTextSimilarBooks.visibility = View.VISIBLE
        binding.tvUserRating.visibility = View.VISIBLE
        binding.tvTextDescription.visibility = View.VISIBLE
        binding.tvTextGenre.visibility = View.VISIBLE
        binding.tvTextPage.visibility = View.VISIBLE
        binding.buttonVote.visibility = View.VISIBLE
        binding.buttonBookLink.visibility = View.VISIBLE
    }

    private fun setView(bookItem: BookItem){

        Glide.with(binding.ivBookImage)
            .load(bookItem.bookImage)
            .into(binding.ivBookImage)
        binding.tvBookTitle.text = bookItem.bookTitle
        binding.tvBookAuthor.text = bookItem.bookAuthor
        binding.tvBookRating.text = bookItem.bookRating.toString()
        binding.tvBookDesciption.text = bookItem.bookDesc
        binding.tvBookGenre.text = bookItem.bookGenre1 + ", " + bookItem.bookGenre2 + ", " + bookItem.bookGenre3
        binding.tvBookPage.text = bookItem.bookPages
        binding.buttonBookLink.setOnClickListener {
            val browserDirect = Intent(Intent.ACTION_VIEW, Uri.parse(bookItem.url))
            startActivity(browserDirect)
        }

        getUserInfo(bookItem.bookTitle)

        binding.buttonVote.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Enter your vote (1-5)")
                val input = EditText(this@BookLayoutActivity)
                input.setHint("Ex: 4.2")
                input.inputType = InputType.TYPE_CLASS_PHONE
                setView(input)

                setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    // Here you get get input text from the Edittext
                    val m_Text = input.text.toString()
                    if (m_Text.toFloat() in 0.0..5.0) {
                        binding.tvUserRating.text = m_Text
                        updateMyBooks(bookItem.iSBN, m_Text.toFloat())
                    } else {
                        Toast.makeText(this@BookLayoutActivity, "Number Invalid", Toast.LENGTH_SHORT).show()
                    }
                })

                setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
                show()
            }
        }


    }

    private fun updateMyBooks(ISBN: String, rating: Float){

        viewModel.updateMyBooks(ISBN, rating, userModel.token.toString()).observe(this){ result ->
            if (result != null) {
                when (result) {
                    is Fetch.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Fetch.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.rvBookListHorizontal.visibility = View.VISIBLE
                        val data = result.data
                        Log.i("TESTING", data.toString())
                        Toast.makeText(
                            this,
                            result.data,
                            Toast.LENGTH_LONG
                        ).show()
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

    private fun getSimilarBooks(){
        val title = intent.getStringExtra(TITLE)
        if (title != null) {
            viewModel.getSimilarBooks(title).observe(this){ result ->
                if (result != null) {
                    when (result) {
                        is Fetch.Loading -> {
                            binding.shimmerLayout.visibility = View.VISIBLE
                        }
                        is Fetch.Success -> {
                            binding.shimmerLayout.visibility = View.GONE
                            binding.rvBookListHorizontal.visibility = View.VISIBLE
                            val data = result.data
                            setSimilarBooks(data)
                        }
                        is Fetch.Error -> {
                            binding.shimmerLayout.visibility = View.GONE
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