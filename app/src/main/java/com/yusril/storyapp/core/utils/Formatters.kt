package com.yusril.storyapp.core.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun dateFormatter(string: String) : String {
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    inputFormatter.timeZone = TimeZone.getTimeZone("UTC")

    val dateParse = inputFormatter.parse(string) as Date
    return SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(dateParse)
}