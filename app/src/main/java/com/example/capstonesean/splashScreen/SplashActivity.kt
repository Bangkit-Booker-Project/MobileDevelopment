package com.example.capstonesean.splashScreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.capstonesean.databinding.ActivitySplashBinding
import com.example.capstonesean.homePage.HomepageActivity
import com.example.capstonesean.model.UserModel
import com.example.capstonesean.model.UserPreferences
import com.example.capstonesean.settings.SettingPreferences
import com.example.capstonesean.settings.SettingViewModel
import com.example.capstonesean.settings.SettingViewModelFactory
import com.example.capstonesean.signUp.SignUpActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var userModel: UserModel
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )

        settingViewModel.getThemeSettings().observe(
            this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            setFlow()
            finish()
        }, 3000)
    }

    private fun setFlow(){
        userPreferences = UserPreferences(this@SplashActivity)
        userModel = userPreferences.getUser()

        if(userModel.isLogin){ startActivity(Intent(this@SplashActivity, HomepageActivity::class.java)) }
        else { startActivity(Intent(this@SplashActivity, SignUpActivity::class.java)) }
    }
}
