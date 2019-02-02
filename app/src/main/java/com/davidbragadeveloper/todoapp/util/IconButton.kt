package com.davidbragadeveloper.todoapp.util

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.TintableBackgroundView
import androidx.core.widget.ImageViewCompat
import com.davidbragadeveloper.todoapp.R

class IconButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    deffStyleAttr: Int = 0
): AppCompatImageButton(context, attributeSet, deffStyleAttr), TintableBackgroundView {

    private var touchableArea = 8

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        changeTouchableArea(touchableArea)
    }

    fun setColorDrawable(color: Int) {
        val newDrawable = ContextCompat.getDrawable(context, R.drawable.ic_high_priority)

        newDrawable?.let {
            val finalDrawable = DrawableCompat.wrap(it)
            DrawableCompat.setTint(finalDrawable.mutate(), color)
            setImageDrawable(finalDrawable)
        }
    }

}