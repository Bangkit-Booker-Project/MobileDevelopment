package com.yusril.storyapp.ui.detail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.yusril.storyapp.core.domain.model.Story
import com.yusril.storyapp.core.utils.dateFormatter
import com.yusril.storyapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val story = intent.getParcelableExtra<Story>(STORY)
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(story?.photoUrl)
                .into(detailImage)
            detailUserName.text = story?.name
            detailDate.text = story?.createdAt?.let { dateFormatter(it) }
            detailDescription.text = story?.description
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        private const val STORY = "story"

        fun start(context: Activity, story: Story) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(STORY, story)
            context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(context).toBundle())
        }
    }
}