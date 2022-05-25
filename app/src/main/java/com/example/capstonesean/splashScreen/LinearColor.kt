package com.example.capstonesean.splashScreen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.example.capstonesean.R

class LinearColor: AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, right, top, left, bottom)

        paint.shader = LinearGradient(0F, 0F, width.toFloat(), height.toFloat(),
            ContextCompat.getColor(context, R.color.light_green),
            ContextCompat.getColor(context, R.color.dark_green),
            Shader.TileMode.CLAMP
        )
    }
}