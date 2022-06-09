package com.yusril.storyapp.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yusril.storyapp.core.data.local.UserPreferences
import com.yusril.storyapp.core.presentation.ViewModelFactory
import com.yusril.storyapp.databinding.ActivitySplashScreenBinding
import com.yusril.storyapp.ui.auth.AuthViewModel
import com.yusril.storyapp.ui.auth.LoginActivity
import com.yusril.storyapp.ui.main.MainActivity
import com.yusril.storyapp.ui.onboarding.OnBoardingActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        viewModel.getCurrentUser().observe(this) {
            Handler(Looper.getMainLooper()).postDelayed({
                if (it.token == "") {
                    viewModel.getOnBoardingKey().observe(this) {
                        if (it) {
                            LoginActivity.start(this)
                        } else {
                            OnBoardingActivity.start(this)
                        }
                    }
                } else {
                    MainActivity.start(this, it)
                }
            }, 3000)
        }

    }

    private fun init() {
        val factory = ViewModelFactory.getInstance(this, UserPreferences.getInstance(dataStore))
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
    }
}