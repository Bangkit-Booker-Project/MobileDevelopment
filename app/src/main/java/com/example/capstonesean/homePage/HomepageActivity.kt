package com.example.capstonesean.homePage

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonesean.R
import com.example.capstonesean.databinding.ActivityHomepageBinding
import com.google.android.material.tabs.TabLayoutMediator


class HomepageActivity : AppCompatActivity() {

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
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_search -> {
                Toast.makeText(applicationContext, "Search", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_user -> {
                Toast.makeText(applicationContext, "User", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}