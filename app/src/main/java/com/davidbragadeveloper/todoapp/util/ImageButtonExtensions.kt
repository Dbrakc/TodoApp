package com.davidbragadeveloper.todoapp.util

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.widget.ImageButton

fun ImageButton.changeTouchableArea(extraSpace: Int) {
    val parent = parent as View
    val touchableArea = Rect()
    this.getHitRect(touchableArea)
    touchableArea.top -= extraSpace
    touchableArea.bottom += extraSpace
    touchableArea.left -= extraSpace
    touchableArea.right += extraSpace
    parent.touchDelegate = TouchDelegate(touchableArea, this)
}