package com.example.viewlabor.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.IBinder
import android.text.Editable
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.RelativeLayout
import com.example.viewlabor.R
import kotlinx.android.synthetic.main.view_password_edittext.view.*

@SuppressLint("ClickableViewAccessibility")
class PasswordEditText : RelativeLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        LayoutInflater.from(context).inflate(R.layout.view_password_edittext, this, true)

        ivPassword.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("ACTION_DOWN", "action_down")
                    setTransformationMethod(null)
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    Log.d("ACTION_DOWN", "action_up")
                    setTransformationMethod(PasswordTransformationMethod.getInstance())
                    true
                }
                else -> false
            }
        }

        setTransformationMethod(PasswordTransformationMethod.getInstance())
    }

    private fun setTransformationMethod(method: TransformationMethod?) {
        val ss = etPassword.selectionStart
        val se = etPassword.selectionEnd
        etPassword.transformationMethod = method
        etPassword.setSelection(ss, se)
    }


}