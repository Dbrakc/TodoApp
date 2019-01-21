package com.davidbragadeveloper.todoapp.util

import android.content.Context
import com.davidbragadeveloper.todoapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


open class BottomSheetDialog(): BottomSheetDialogFragment() {
    override fun getTheme(): Int = R.style.dialog
}