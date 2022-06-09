package com.yusril.storyapp.ui.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.yusril.storyapp.R

class MyPasswordEditText : MyEditText {
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private lateinit var eyeImage : Drawable
    private var isHidePassword = true

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setCompoundDrawablesWithIntrinsicBounds(null, null, eyeImage, null)
        hidePassword(isHidePassword)
    }

    private fun init() {
        eyeImage = ContextCompat.getDrawable(context, R.drawable.ic_eye_active) as Drawable
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // nothing
            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, count: Int) {
                error = if (s.length in 1..5) resources.getString(R.string.password_error) else null
            }

            override fun afterTextChanged(p0: Editable?) {
                // nothing
            }
        })
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val buttonStart: Float
            val buttonEnd: Float
            var isButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                buttonEnd = (eyeImage.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < buttonEnd -> isButtonClicked = true
                }
            } else {
                buttonStart = (width - paddingEnd - eyeImage.intrinsicWidth).toFloat()
                when {
                    event.x > buttonStart -> isButtonClicked = true
                }
            }
            if (isButtonClicked) {
                if (event.action == MotionEvent.ACTION_UP) {
                    hidePassword(isHidePassword)
                    isHidePassword = !isHidePassword
                    return true
                }
            } else return false
        }
        return false
    }

    private fun hidePassword(isHide: Boolean) {
        if (isHide) {
            eyeImage = ContextCompat.getDrawable(context, R.drawable.ic_eye) as Drawable
            setTextColor(ContextCompat.getColor(context, R.color.purple_500))
            transformationMethod = PasswordTransformationMethod.getInstance()
        } else {
            eyeImage = ContextCompat.getDrawable(context, R.drawable.ic_eye_active) as Drawable
            setTextColor(ContextCompat.getColor(context, R.color.black))
            transformationMethod = HideReturnsTransformationMethod.getInstance()
        }
    }
}