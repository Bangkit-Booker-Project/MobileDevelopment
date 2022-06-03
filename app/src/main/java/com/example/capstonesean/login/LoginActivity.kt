package com.example.capstonesean.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.capstonesean.R
import com.example.capstonesean.databinding.ActivityLoginBinding
import com.example.capstonesean.databinding.ActivityRegisterBinding
import com.example.capstonesean.genrePick.GenrePickActivity
import com.example.capstonesean.signUp.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setMyLoginButtonEnable()
        binding.signupText.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, GenrePickActivity::class.java)
            startActivity(intent)
        }

        playAnimation()

        binding.emailText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing
            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                setMyLoginButtonEnable()
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
                setMyLoginButtonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Nothing
            }

        })
    }

    private fun setMyLoginButtonEnable() {
        val emailResult = binding.emailText.text
        val passwordResult = binding.passwordText.text
        val isValidEmail = emailResult != null && emailResult.toString().isNotEmpty()
        val isValidPassword = passwordResult != null && passwordResult.toString().isNotEmpty()
        val isNotError =
            binding.passwordText.error == null && binding.emailText.error == null
        binding.loginButton.isEnabled = isValidEmail && isValidPassword && isNotError
    }

    private fun playAnimation() {
        val login = ObjectAnimator.ofFloat(binding.login, View.ALPHA, 1f).setDuration(1000)
        val logo = ObjectAnimator.ofFloat(binding.bookerLogo, View.ALPHA, 1f).setDuration(1000)
        val appname = ObjectAnimator.ofFloat(binding.bookerText, View.ALPHA, 1f).setDuration(1000)
        val email = ObjectAnimator.ofFloat(binding.emailText, View.ALPHA, 1f).setDuration(1000)
        val password =
            ObjectAnimator.ofFloat(binding.passwordText, View.ALPHA, 1f).setDuration(1000)
        val donthaveacc =
            ObjectAnimator.ofFloat(binding.dontHaveAccount, View.ALPHA, 1f).setDuration(1000)
        val signup = ObjectAnimator.ofFloat(binding.signupText, View.ALPHA, 1f).setDuration(1000)
        val loginbutton =
            ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(1000)

        val together = AnimatorSet().apply {
            playTogether(email, password)
        }

        AnimatorSet().apply {
            playSequentially(login, logo, appname, together, donthaveacc, signup, loginbutton)
            start()
        }
    }
}