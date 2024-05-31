package com.c241ps093.ezfarm.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.c241ps093.ezfarm.R

class CustomButton : AppCompatButton {
    private lateinit var enabledBackground: Drawable
    private lateinit var disabledBackground: Drawable
    private lateinit var iconDrawable: Drawable
    private var txtColor: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if (isEnabled) enabledBackground else disabledBackground
        setTextColor(txtColor)
        textSize = 18f
        gravity = Gravity.CENTER
        text = if (isEnabled) "Submit" else "Enter your data First!"
    }

    private fun init() {
        txtColor = ContextCompat.getColor(context, R.color.white)
        enabledBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_button_primary) as Drawable
        disabledBackground =
            ContextCompat.getDrawable(context, R.drawable.bg_button_primary_disable) as Drawable

        iconDrawable = ContextCompat.getDrawable(context, R.drawable.add_ic) as Drawable
    }

    override fun setCompoundDrawables(
        left: Drawable?, top: Drawable?, right: Drawable?, bottom: Drawable?
    ) {
        super.setCompoundDrawables(iconDrawable, null, null, null) // Set icon only
        val padding = 16 // Adjust padding as needed
        compoundDrawablePadding = padding
    }
}