package com.example.capstonesean.profilepage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.capstonesean.R
import com.example.capstonesean.databinding.ActivityProfilePageBinding
import com.example.capstonesean.editprofile.EditProfileActivity
import com.example.capstonesean.model.UserModel
import com.example.capstonesean.model.UserPreferences
import com.example.capstonesean.settings.SettingsActivity
import com.example.capstonesean.signUp.SignUpActivity

class ProfilePageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilePageBinding
    private lateinit var userModel: UserModel
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.show()
        super.onCreate(savedInstanceState)
        binding = ActivityProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this@ProfilePageActivity)
        userModel = userPreferences.getUser()

        setUserData()

        binding.settingButton.setOnClickListener { settings() }
        binding.logoutButton.setOnClickListener { logout() }
    }

    private fun setUserData(){


        Glide.with(binding.ivProfilePic)
            .load(R.drawable.logobooker)
            .circleCrop()
            .into(binding.ivProfilePic)
        binding.tvUsername.text = userModel.username

//        binding.ivProfilePic.setImageResource(R.drawable.sample_avatar)
    }

    private fun myBooks(){
        val moveToProfilePage = Intent(this, EditProfileActivity::class.java)
        startActivity(moveToProfilePage)
    }

    private fun settings(){
        val moveToSettingPage = Intent(this, SettingsActivity::class.java)
        startActivity(moveToSettingPage)
    }

    private fun logout(){
        val userPreferences = UserPreferences(this)
        userPreferences.setUser(UserModel(username = null, token = null, userId = null, isLogin = false))
        Log.d("TOKEN", userModel.token.toString())
        AlertDialog.Builder(this).apply {
            setTitle("Logout " + getString(R.string.success))
            setPositiveButton("OK") { _, _ ->
                val intent = Intent(this@ProfilePageActivity, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }
}