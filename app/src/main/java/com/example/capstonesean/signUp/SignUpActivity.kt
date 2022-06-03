package com.example.capstonesean.signUp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import com.example.capstonesean.R
import com.example.capstonesean.databinding.ActivityRegisterBinding
import com.example.capstonesean.databinding.ActivitySplashBinding
import com.example.capstonesean.login.LoginActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()

        setMyButtonEnable()
        binding.loginText.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        //binding.signUpButton.setOnClickListener { register() }

        binding.usernameText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing
            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Nothing
            }

        })

        binding.emailText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing
            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Nothing
            }

        })

        binding.passwordText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing
            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Nothing
            }

        })
    }

    private fun playAnimation() {
        val signup = ObjectAnimator.ofFloat(binding.signUp, View.ALPHA, 1f).setDuration(1000)
        val logo = ObjectAnimator.ofFloat(binding.bookerLogo, View.ALPHA, 1f).setDuration(1000)
        val appname = ObjectAnimator.ofFloat(binding.bookerText, View.ALPHA, 1f).setDuration(1000)
        val username = ObjectAnimator.ofFloat(binding.usernameText, View.ALPHA, 1f).setDuration(1000)
        val email = ObjectAnimator.ofFloat(binding.emailText, View.ALPHA, 1f).setDuration(1000)
        val password = ObjectAnimator.ofFloat(binding.passwordText, View.ALPHA, 1f).setDuration(1000)
        val haveacc = ObjectAnimator.ofFloat(binding.haveAccount, View.ALPHA, 1f).setDuration(1000)
        val logintext = ObjectAnimator.ofFloat(binding.loginText, View.ALPHA, 1f).setDuration(1000)
        val signbutton = ObjectAnimator.ofFloat(binding.signUpButton, View.ALPHA, 1f).setDuration(1000)

        val together = AnimatorSet().apply {
            playTogether(username, email, password)
        }

        AnimatorSet().apply {
            playSequentially(signup, logo, appname, together, haveacc, logintext, signbutton)
            start()
        }
    }


    private fun setMyButtonEnable() {
        val usernameResult = binding.usernameText.text
        val emailResult = binding.emailText.text
        val passwordResult = binding.passwordText.text
        val isValidName = usernameResult != null && usernameResult.toString().isNotEmpty()
        val isValidEmail = emailResult != null && emailResult.toString().isNotEmpty()
        val isValidPassword = passwordResult != null && passwordResult.toString().isNotEmpty()
        val isNotError =
            binding.passwordText.error == null && binding.emailText.error == null && binding.usernameText.error == null
        binding.signUpButton.isEnabled = isValidName && isValidEmail && isValidPassword && isNotError
    }
}