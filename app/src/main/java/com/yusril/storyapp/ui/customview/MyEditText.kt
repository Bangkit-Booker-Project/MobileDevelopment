package com.yusril.storyapp.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.yusril.storyapp.R

open class MyEditText : AppCompatEditText, View.OnTouchListener {
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private lateinit var border : Drawable

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = border
        setPaddingRelative(48, 48, 48, 48)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        return false
    }

    private fun init() {
        border = ContextCompat.getDrawable(context, R.drawable.border) as Drawable
        setOnTouchListener(this)
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Nothing
            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                error = if (s.isEmpty()) resources.getString(R.string.empty_field) else null
            }

            override fun afterTextChanged(p0: Editable?) {
                // Nothing
            }

        })
    }
}