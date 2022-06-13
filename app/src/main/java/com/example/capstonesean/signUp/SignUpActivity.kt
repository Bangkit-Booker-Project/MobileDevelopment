package com.example.capstonesean.signUp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.capstonesean.R
import com.example.capstonesean.data.Fetch
import com.example.capstonesean.data.response.LoginResult
import com.example.capstonesean.databinding.ActivityRegisterBinding
import com.example.capstonesean.genrePick.GenrePickActivity
import com.example.capstonesean.login.LoginActivity
import com.example.capstonesean.model.UserModel
import com.example.capstonesean.model.UserPreferences
import com.example.capstonesean.viewmodelfactory.UserModelFactory

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userModel: UserModel
    private lateinit var userPreferences: UserPreferences

    val viewModel: SignUpViewModel by viewModels{
        UserModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()

        binding.progressBar.visibility = View.INVISIBLE

        setMyButtonEnable()
        binding.loginText.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signUpButton.setOnClickListener {
            val username = binding.usernameText.text.toString()
            val email = binding.emailText.text.toString()
            val password = binding.passwordText.text.toString()

            viewModel.userRegister(username, email, password).observe(this@SignUpActivity){ result ->
                if (result != null) {
                    when (result) {
                        is Fetch.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Fetch.Success -> {
                            binding.progressBar.visibility = View.GONE
                            val data = result.data
                            Toast.makeText(
                                this,
                                "Status : $data",
                                Toast.LENGTH_SHORT
                            ).show()
                            AlertDialog.Builder(this).apply {
                                setTitle("Signup " + getString(R.string.success))
                                setPositiveButton("OK") { _, _ ->
                                    setUserData(email, password)
                                }
                                create()
                                show()
                            }
                        }
                        is Fetch.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }


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

    private fun setUserData(email: String, password: String){
//        val email = binding.emailText.text.toString()
//        val password = binding.passwordText.text.toString()

        viewModel.userLogin(email, password).observe(this@SignUpActivity){ result ->
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

    private fun inputData(data: LoginResult){
        val userPreferences = UserPreferences(this@SignUpActivity)
        userPreferences.setUser(UserModel(username = data.username, token = data.token, isLogin = true))
        Log.i("TOKEN SIGNUP", data.token)
        val intent = Intent(this@SignUpActivity, GenrePickActivity::class.java)
        startActivity(intent)
        finish()
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