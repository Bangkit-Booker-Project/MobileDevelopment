package com.yusril.storyapp.ui.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yusril.storyapp.R
import com.yusril.storyapp.core.data.local.UserPreferences
import com.yusril.storyapp.core.presentation.ViewModelFactory
import com.yusril.storyapp.core.vo.Status
import com.yusril.storyapp.databinding.ActivityRegisterBinding

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        setMyButtonEnable()
        binding.btnLogin.setOnClickListener { LoginActivity.start(this) }
        binding.btnRegister.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            register()
        }

        binding.inputName.addTextChangedListener(object : TextWatcher{
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

        binding.inputEmail.addTextChangedListener(object : TextWatcher{
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

        binding.inputPassword.addTextChangedListener(object : TextWatcher {
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

    private fun register() {
        val name = binding.inputName.text?.trim().toString()
        val email = binding.inputEmail.text?.trim().toString()
        val password = binding.inputPassword.text?.trim().toString()
        viewModel.register(name, email, password).observe(this){
            when(it.status) {
                Status.LOADING -> {
                    showLoading(true)
                    Log.d("RegisterResult", "Loading")
                }
                Status.ERROR -> {
                    showLoading(false)
                    Toast.makeText(this, "error, ${it.message}", Toast.LENGTH_SHORT).show()
                    Log.d("RegisterResult", "error, ${it.message}")
                }
                Status.SUCCESS -> {
                    showLoading(false)
                    Toast.makeText(this, "success, ${it.data?.message}", Toast.LENGTH_SHORT).show()
                    LoginActivity.start(this)
                }
            }
        }
    }

    private fun init() {
        val factory = ViewModelFactory.getInstance(this, UserPreferences.getInstance(dataStore))
        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
    }

    private fun showLoading(show: Boolean) {
        if (show) {
            binding.btnRegister.apply {
                text = ""
                isEnabled = false
            }
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.btnRegister.apply {
                text = resources.getString(R.string.register)
                isEnabled = true
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setMyButtonEnable() {
        val nameResult = binding.inputName.text
        val emailResult = binding.inputEmail.text
        val passwordResult = binding.inputPassword.text
        val isValidName = nameResult != null && nameResult.toString().isNotEmpty()
        val isValidEmail = emailResult != null && emailResult.toString().isNotEmpty()
        val isValidPassword = passwordResult != null && passwordResult.toString().isNotEmpty()
        val isNotError = binding.inputPassword.error == null && binding.inputEmail.error == null && binding.inputName.error == null
        binding.btnRegister.isEnabled = isValidName && isValidEmail && isValidPassword && isNotError
    }

    companion object {
        fun start(context: Activity?) {
            val intent = Intent(context, RegisterActivity::class.java)
            context?.startActivity(intent)
        }
    }
}