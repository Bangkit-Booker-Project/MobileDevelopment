package com.example.capstonesean.signUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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