package com.example.capstonesean.genrePick


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import com.example.capstonesean.R
import com.example.capstonesean.booklayout.BookLayoutViewModel
import com.example.capstonesean.data.Fetch
import com.example.capstonesean.databinding.ActivityGenrePickBinding
import com.example.capstonesean.homePage.HomepageActivity
import com.example.capstonesean.model.UserModel
import com.example.capstonesean.model.UserPreferences
import com.example.capstonesean.viewmodelfactory.BookModelFactory

class GenrePickActivity : AppCompatActivity() {

    private var count = 0
    private lateinit var binding: ActivityGenrePickBinding
    private lateinit var userModel: UserModel
    private lateinit var userPreferences: UserPreferences
    val viewModel: BookLayoutViewModel by viewModels {
        BookModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityGenrePickBinding.inflate(layoutInflater)

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()

        binding.progressBar.visibility = View.INVISIBLE

        setContentView(binding.root)
        setGenreButtonEnable()
        binding.genreButton.setOnClickListener {
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
        }
        playAnimation()
    }

    private fun setGenreButtonEnable() {
        val fiction = findViewById<CheckBox>(R.id.genre1)
        val historical = findViewById<CheckBox>(R.id.genre2)
        val fantasy = findViewById<CheckBox>(R.id.genre3)
        val classic = findViewById<CheckBox>(R.id.genre4)
        val sciFi = findViewById<CheckBox>(R.id.genre5)
        val nonFiction = findViewById<CheckBox>(R.id.genre6)
        val romance = findViewById<CheckBox>(R.id.genre7)
        val mystery = findViewById<CheckBox>(R.id.genre8)
        val horror = findViewById<CheckBox>(R.id.genre9)
        val button = findViewById<AppCompatButton>(R.id.genre_button)

        fiction.setOnClickListener {
            if (fiction.isChecked) {
                count++
                addToList(375825649.toString())
            } else {
                count--
            }
            onCheckChange()
        }
        historical.setOnClickListener {
            if (historical.isChecked) {
                count++
                addToList(446312967.toString())
            } else {
                count--
            }
            onCheckChange()
        }
        fantasy.setOnClickListener {
            if (fantasy.isChecked) {
                count++
                addToList(60013117.toString())
            } else {
                count--
            }
            onCheckChange()
        }
        classic.setOnClickListener {
            if (classic.isChecked) {
                count++
                addToList(140049975.toString())
            } else {
                count--
            }
            onCheckChange()
        }
        sciFi.setOnClickListener {
            if (sciFi.isChecked) {
                count++
                addToList(671578855.toString())
            } else {
                count--
            }
            onCheckChange()
        }
        nonFiction.setOnClickListener {
            if (nonFiction.isChecked) {
                count++
                addToList(812933176.toString())
            } else {
                count--
            }
            onCheckChange()
        }
        romance.setOnClickListener {
            if (romance.isChecked) {
                count++
                addToList(553252038.toString())
            } else {
                count--
            }
            onCheckChange()
        }
        mystery.setOnClickListener {
            if (mystery.isChecked) {
                count++
                addToList(671023187.toString())
            } else {
                count--
            }
            onCheckChange()
        }
        horror.setOnClickListener {
            if (horror.isChecked) {
                count++
                addToList(671032658.toString())
            } else {
                count--
            }
            onCheckChange()
        }
        button.isEnabled = false
    }

    private fun onCheckChange() {
        binding.genreButton.isEnabled = count > 0
        }

    private fun addToList(ISBN: String){
        Log.i("TOKEN GENREPICK", userModel.token.toString())
        viewModel.updateMyBooks(ISBN, 5.01.toFloat(), userModel.token.toString()).observe(this){ result ->
            if (result != null) {
                when (result) {
                    is Fetch.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Fetch.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val data = result.data
                        Log.i("TESTING", data.toString())
                    }
                    is Fetch.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            result.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun playAnimation() {
        val pickgenre = ObjectAnimator.ofFloat(binding.pickGenre, View.ALPHA, 1f).setDuration(1000)
        val prefgenre = ObjectAnimator.ofFloat(binding.preferredGenre, View.ALPHA, 1f).setDuration(1000)
        val genre1 = ObjectAnimator.ofFloat(binding.genre1, View.ALPHA, 1f).setDuration(1000)
        val genre2 = ObjectAnimator.ofFloat(binding.genre2, View.ALPHA, 1f).setDuration(1000)
        val genre3 = ObjectAnimator.ofFloat(binding.genre3, View.ALPHA, 1f).setDuration(1000)
        val genre4 = ObjectAnimator.ofFloat(binding.genre4, View.ALPHA, 1f).setDuration(1000)
        val genre5 = ObjectAnimator.ofFloat(binding.genre5, View.ALPHA, 1f).setDuration(1000)
        val genre6 = ObjectAnimator.ofFloat(binding.genre6, View.ALPHA, 1f).setDuration(1000)
        val genre7 = ObjectAnimator.ofFloat(binding.genre7, View.ALPHA, 1f).setDuration(1000)
        val genre8 = ObjectAnimator.ofFloat(binding.genre8, View.ALPHA, 1f).setDuration(1000)
        val genre9 = ObjectAnimator.ofFloat(binding.genre9, View.ALPHA, 1f).setDuration(1000)
        val genrebutton = ObjectAnimator.ofFloat(binding.genreButton, View.ALPHA, 1f).setDuration(1000)

        val together = AnimatorSet().apply {
            playTogether(genre1, genre2, genre3, genre4, genre5, genre6, genre7, genre8, genre9)
        }

        AnimatorSet().apply {
            playSequentially(pickgenre, prefgenre, together, genrebutton)
            start()
        }
    }
}