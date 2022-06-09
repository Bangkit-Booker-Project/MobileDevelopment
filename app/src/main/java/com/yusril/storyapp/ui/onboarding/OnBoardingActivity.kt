package com.yusril.storyapp.ui.onboarding

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yusril.storyapp.core.data.local.UserPreferences
import com.yusril.storyapp.core.presentation.ViewModelFactory
import com.yusril.storyapp.databinding.ActivityOnBoardingBinding
import com.yusril.storyapp.ui.auth.AuthViewModel
import com.yusril.storyapp.ui.auth.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        binding.button.setOnClickListener {
            viewModel.setOnBoardingKey(true)
            LoginActivity.start(this)
        }
    }

    private fun init() {
        val factory = ViewModelFactory.getInstance(this, UserPreferences.getInstance(dataStore))
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
    }


    companion object {
        fun start(context : Activity?) {
            val intent = Intent(context, OnBoardingActivity::class.java)
            context?.startActivity(intent)
        }
    }
}