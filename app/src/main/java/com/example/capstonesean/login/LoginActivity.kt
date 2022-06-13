package com.example.capstonesean.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.capstonesean.R
import com.example.capstonesean.data.Fetch
import com.example.capstonesean.data.response.LoginResult
import com.example.capstonesean.databinding.ActivityLoginBinding
import com.example.capstonesean.homePage.HomepageActivity
import com.example.capstonesean.model.UserModel
import com.example.capstonesean.model.UserPreferences
import com.example.capstonesean.signUp.SignUpActivity
import com.example.capstonesean.viewmodelfactory.UserModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userModel: UserModel
    private lateinit var userPreferences: UserPreferences

    val viewModel: LoginViewModel by viewModels{
        UserModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.INVISIBLE
        setMyLoginButtonEnable()
        binding.signupText.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()

            viewModel.userLogin(email, password).observe(this@LoginActivity){ result ->
                if (result != null) {
                    when (result) {
                        is Fetch.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Fetch.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val data = result.data
                            inputData(data)
                        }
                        is Fetch.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                "Password atau email salah",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

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

    private fun inputData(data: LoginResult){
        val userPreferences = UserPreferences(this@LoginActivity)
        userPreferences.setUser(UserModel(username = data.username, token = data.token, isLogin = true))
        AlertDialog.Builder(this).apply {
            setTitle("Login " + getString(R.string.success))
            setPositiveButton("OK") { _, _ ->
                val intent = Intent(this@LoginActivity, HomepageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
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