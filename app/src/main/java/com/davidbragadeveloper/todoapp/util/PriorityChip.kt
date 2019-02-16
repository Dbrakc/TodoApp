package com.davidbragadeveloper.todoapp.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.HighPriority
import com.google.android.material.chip.Chip
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit


class PriorityChip (context: Context, attrs: AttributeSet) : Chip( context, attrs) {

    val COLOR_PRIMARY = ContextCompat.getColor(context, R.color.colorPrimary)

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

        init {
            chipBackgroundColor = ColorStateList.valueOf(Color.BLUE)
            text = "Low Priority"
            isCheckedIconVisible = false
            isChipIconVisible = false
            textSize = 20f
            this
                .clicks()
                .throttleFirst(600, TimeUnit.MILLISECONDS)
                .subscribe {
                    when(getHighPriority()) {
                        HighPriority.HIGH ->  setColorForHighPriority(HighPriority.LOW)
                        HighPriority.MEDIUM -> setColorForHighPriority(HighPriority.HIGH)
                        HighPriority.LOW -> setColorForHighPriority(HighPriority.MEDIUM)
                    }
                }.addTo(compositeDisposable)
        }

        fun setColorForHighPriority (highPriority: HighPriority) {
            when (highPriority) {
                HighPriority.HIGH -> {
                    chipBackgroundColor=ColorStateList.valueOf(Color.RED)
                    text=context.getString(R.string.hight_priority_text)
                }
                HighPriority.MEDIUM -> {
                    chipBackgroundColor=ColorStateList.valueOf(COLOR_PRIMARY)
                    text=context.getString(R.string.medium_priority_text)
                }
                HighPriority.LOW ->{
                    chipBackgroundColor = ColorStateList.valueOf(Color.BLUE)
                    text=context.getString(R.string.low_priority_text)
                }
            }
        }

        fun getHighPriority(): HighPriority = when(chipBackgroundColor){
            ColorStateList.valueOf(Color.RED) -> HighPriority.HIGH
            ColorStateList.valueOf(COLOR_PRIMARY) -> HighPriority.MEDIUM
            ColorStateList.valueOf(Color.BLUE) -> HighPriority.LOW
            else -> HighPriority.LOW
        }
    }






