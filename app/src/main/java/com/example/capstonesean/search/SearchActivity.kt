package com.example.capstonesean.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonesean.R
import com.example.capstonesean.data.Fetch
import com.example.capstonesean.data.response.BookItem
import com.example.capstonesean.databinding.ActivitySearchBinding
import com.example.capstonesean.profilepage.ProfilePageActivity
import com.example.capstonesean.viewmodelfactory.BookModelFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var toolbar: Toolbar

    val viewModel: SearchViewModel by viewModels {
        BookModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.tvSearchResult.visibility = View.INVISIBLE
        val layoutManager = LinearLayoutManager(this)
        binding.rvSearch.layoutManager = layoutManager

        val query = intent.getStringExtra(QUERY)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        setData(query)
    }

    private fun setData(query: String?){
//        Toast.makeText(this, query, Toast.LENGTH_SHORT).show()
        if (query != null) {
            viewModel.searchBooks(query).observe(this){ result ->
                if (result != null) {
                    when (result) {
                        is Fetch.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Fetch.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val data = result.data
                            setView(data, query)
                        }
                        is Fetch.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                "Book Not Found",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun setView(data: List<BookItem>, query: String?){
        val adapter = BookSimpleAdapter(data)
        Log.i("tes", data.toString())
        binding.rvSearch.adapter = adapter

        binding.tvSearchResult.visibility = View.VISIBLE
        binding.tvSearchResult.text = getString(R.string.search_result, data.size, query)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.action_bar, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.menu_search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                setData(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_user -> {
                val moveToProfilePage = Intent(this, ProfilePageActivity::class.java)
                startActivity(moveToProfilePage)
            }
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
            }

        }
        return true
    }

    companion object{
        const val QUERY = "query"
    }
}