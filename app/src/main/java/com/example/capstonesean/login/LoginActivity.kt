package com.example.capstonesean.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        binding.signupText.setOnClickListener { val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
        startActivity(intent)}
        binding.loginButton.setOnClickListener { val intent = Intent(this@LoginActivity, GenrePickActivity::class.java)
        startActivity(intent)}

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
}