package com.yusril.storyapp.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.yusril.storyapp.R

class MyLogo : View {
    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var halfOfHeight = 0
    private var halfOfWidth = 0
    private val borderPaint = Paint()

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        halfOfHeight = height/2
        halfOfWidth = width/2

        canvas?.save()
        showCircleBorder(canvas)
        canvas?.restore()
    }

    private fun showCircleBorder(canvas: Canvas?) {
        canvas?.translate(halfOfWidth - 50F, halfOfHeight - 50F)
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = 30F
        borderPaint.shader = LinearGradient(0F, 100F, 100F, 0F,
            ContextCompat.getColor(context, R.color.yellow),
            ContextCompat.getColor(context, R.color.purple_900),
            Shader.TileMode.CLAMP
        )
        canvas?.drawRoundRect(0F, 0F, 100F, 100F, 50F, 50F, borderPaint)
    }
}