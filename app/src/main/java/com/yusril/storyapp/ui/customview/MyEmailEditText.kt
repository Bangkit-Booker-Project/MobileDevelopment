package com.yusril.storyapp.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.yusril.storyapp.R

class MyEmailEditText : MyEditText {
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // nothing
            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, count: Int) {
                error = if (isEmailValid(s)) null else resources.getString(R.string.email_error)
            }

            override fun afterTextChanged(p0: Editable?) {
                // nothing
            }
        })
    }

    private fun isEmailValid(email: CharSequence) : Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}