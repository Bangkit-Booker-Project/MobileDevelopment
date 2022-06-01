package com.example.capstonesean.genrePick


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.capstonesean.R
import com.example.capstonesean.databinding.ActivityGenrePickBinding
import com.example.capstonesean.homePage.HomepageActivity

class GenrePickActivity : AppCompatActivity() {
    private var count = 0
    private lateinit var binding: ActivityGenrePickBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityGenrePickBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setGenreButtonEnable()
        binding.genreButton.setOnClickListener {
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setGenreButtonEnable() {
        val genre1 = findViewById<CheckBox>(R.id.genre1)
        val genre2 = findViewById<CheckBox>(R.id.genre2)
        val genre3 = findViewById<CheckBox>(R.id.genre3)
        val genre4 = findViewById<CheckBox>(R.id.genre4)
        val genre5 = findViewById<CheckBox>(R.id.genre5)
        val genre6 = findViewById<CheckBox>(R.id.genre6)
        val genre7 = findViewById<CheckBox>(R.id.genre7)
        val genre8 = findViewById<CheckBox>(R.id.genre8)
        val genre9 = findViewById<CheckBox>(R.id.genre9)
        val button = findViewById<AppCompatButton>(R.id.genre_button)

        genre1.setOnClickListener {
            if (genre1.isChecked) {
                count++
            } else {
                count--
            }
            onCheckChange()
        }
        genre2.setOnClickListener {
            if (genre2.isChecked) {
                count++
            } else {
                count--
            }
            onCheckChange()
        }
        genre3.setOnClickListener {
            if (genre3.isChecked) {
                count++
            } else {
                count--
            }
            onCheckChange()
        }
        genre4.setOnClickListener {
            if (genre4.isChecked) {
                count++
            } else {
                count--
            }
            onCheckChange()
        }
        genre5.setOnClickListener {
            if (genre5.isChecked) {
                count++
            } else {
                count--
            }
            onCheckChange()
        }
        genre6.setOnClickListener {
            if (genre6.isChecked) {
                count++
            } else {
                count--
            }
            onCheckChange()
        }
        genre7.setOnClickListener {
            if (genre7.isChecked) {
                count++
            } else {
                count--
            }
            onCheckChange()
        }
        genre8.setOnClickListener {
            if (genre8.isChecked) {
                count++
            } else {
                count--
            }
            onCheckChange()
        }
        genre9.setOnClickListener {
            if (genre9.isChecked) {
                count++
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
    }