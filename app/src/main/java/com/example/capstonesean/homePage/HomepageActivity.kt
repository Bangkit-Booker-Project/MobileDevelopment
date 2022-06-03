package com.example.capstonesean.homePage

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.capstonesean.R
import com.example.capstonesean.databinding.ActivityHomepageBinding
import com.google.android.material.tabs.TabLayoutMediator


class HomepageActivity : AppCompatActivity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivityHomepageBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        with(binding){
            viewPager.adapter = viewPagerAdapter
            
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = "For You"
                    1 -> tab.text = "Top Rated"
                    2 -> tab.text = "My Books"
                }
            } .attach()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)

        val search = menu?.findItem(R.id.menu_search)
        val searchView = search?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_user -> {
                Toast.makeText(this@HomepageActivity, "User", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            binding.noResults.isVisible
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            binding.noResults.isVisible
        }
        return true
    }
}