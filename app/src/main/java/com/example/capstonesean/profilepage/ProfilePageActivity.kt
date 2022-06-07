package com.example.capstonesean.profilepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstonesean.R
import com.example.capstonesean.databinding.ActivityProfilePageBinding
import com.example.capstonesean.settings.SettingsActivity

class ProfilePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.show()
        super.onCreate(savedInstanceState)
        binding = ActivityProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivProfilePic.setImageResource(R.drawable.sample_avatar)

        binding.mybooksButton.setOnClickListener { myBooks() }
        binding.settingButton.setOnClickListener { settings() }
        binding.logoutButton.setOnClickListener { logout() }
    }

    private fun myBooks(){
        val moveToProfilePage = Intent(this, ProfilePageActivity::class.java)
        startActivity(moveToProfilePage)
    }

    private fun settings(){
        val moveToSettingPage = Intent(this, SettingsActivity::class.java)
        startActivity(moveToSettingPage)
    }

    private fun logout(){}
}