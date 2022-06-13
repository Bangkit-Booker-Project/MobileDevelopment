package com.example.capstonesean.homePage

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.capstonesean.R
import com.example.capstonesean.databinding.ActivityHomepageBinding
import com.example.capstonesean.profilepage.ProfilePageActivity
import com.example.capstonesean.search.SearchActivity
import com.example.capstonesean.viewmodelfactory.BookModelFactory
import com.google.android.material.tabs.TabLayoutMediator


class HomepageActivity : AppCompatActivity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivityHomepageBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    val viewModel: HomepageViewModel by viewModels {
        BookModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        playAnimation()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_user -> {
                val moveToProfilePage = Intent(this, ProfilePageActivity::class.java)
                startActivity(moveToProfilePage)
            }
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            binding.noResults.isVisible
        }
//        Toast.makeText(this, query.toString(), Toast.LENGTH_SHORT).show()

        val moveToSearch = Intent(this, SearchActivity::class.java)
        moveToSearch.putExtra(SearchActivity.QUERY, query.toString())
        startActivity(moveToSearch)

        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            binding.noResults.isVisible
        }
        return true
    }
    private fun playAnimation() {
        val appbar = ObjectAnimator.ofFloat(binding.appBarLayout, View.ALPHA, 1f).setDuration(1000)
        val toolbar = ObjectAnimator.ofFloat(binding.toolbar, View.ALPHA, 1f).setDuration(1000)
        val tabs = ObjectAnimator.ofFloat(binding.tabs, View.ALPHA, 1f).setDuration(1000)
        val viewpager = ObjectAnimator.ofFloat(binding.viewPager, View.ALPHA, 1f).setDuration(1000)


        val together = AnimatorSet().apply {
            playTogether(appbar, toolbar)
        }

        AnimatorSet().apply {
            playSequentially(together, tabs, viewpager)
            start()
        }
    }
}