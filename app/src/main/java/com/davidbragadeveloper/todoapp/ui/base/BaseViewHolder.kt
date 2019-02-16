package com.davidbragadeveloper.todoapp.ui.base

import android.animation.ValueAnimator
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.View
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Taskeable
import com.davidbragadeveloper.todoapp.util.IconButton

abstract class BaseViewHolder <T: Taskeable>  (val view: View): RecyclerView.ViewHolder(view){
        abstract fun bind(taskeable: T)



}