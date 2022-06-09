package com.yusril.storyapp.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.yusril.storyapp.R

class MyGradientText : AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        paint.shader = LinearGradient(0F, 0F, width.toFloat(), height.toFloat(),
            ContextCompat.getColor(context, R.color.yellow),
            ContextCompat.getColor(context, R.color.purple_900),
            Shader.TileMode.CLAMP
        )
    }
}