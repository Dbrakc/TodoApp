package com.davidbragadeveloper.todoapp.ui.subtask

import android.animation.ValueAnimator
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.DiffUtil
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Subtask
import com.davidbragadeveloper.todoapp.ui.base.BaseAdapter
import com.davidbragadeveloper.todoapp.util.DateHelper
import com.davidbragadeveloper.todoapp.util.IconButton
import com.davidbragadeveloper.todoapp.util.Navigation
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.longClicks
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.item_task.view.*
import java.util.concurrent.TimeUnit

class SubtaskAdapter(
    val onTaskMarked: (Subtask, Boolean) -> Unit,
    val onTaskLongClicked: (Subtask) -> Unit,
    val onTaskHighPriorityMarked: (Subtask, Boolean) -> Unit
): BaseAdapter<Subtask>(
    object : DiffUtil.ItemCallback<Subtask>() {
        override fun areItemsTheSame(oldItem: Subtask, newItem: Subtask): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Subtask, newItem: Subtask): Boolean = oldItem == newItem

    }

) {
    override fun bindInViewHolder(taskeable: Subtask, view: View) {
        with(view){
            Log.d("subtak_id", taskeable.id.toString())
            if(taskeable.isDone){
                applyStrikethrough(contentTextView, taskeable.content)

            }else {
                removeStrikethrough(contentTextView, taskeable.content)
            }


            applyColorToHighPriority(buttonHighPriority, taskeable.isHighPriority)

            dateTextView.text = DateHelper.calculateTimaAgo(taskeable.createdAt)
            isDoneCheckBox.isChecked = taskeable.isDone

            isDoneCheckBox
                .clicks()
                .throttleFirst(600, TimeUnit.MILLISECONDS)
                .subscribe{
                    val isChecked = !taskeable.isDone

                    onTaskMarked(taskeable, isChecked)
                    isDoneCheckBox
                        .animate()
                        .rotationBy(360f)
                        .setDuration(600)
                        .setInterpolator(FastOutSlowInInterpolator())
                        .start()
                    executeAnimation(view, isChecked)
                }
                .addTo(componentDisposable)

            this
                .longClicks()
                .throttleFirst(600, TimeUnit.MILLISECONDS)
                .subscribe{
                    onTaskLongClicked(taskeable)
                }
                .addTo(componentDisposable)

            buttonHighPriority
                .clicks()
                .throttleFirst(600, TimeUnit.MILLISECONDS)
                .subscribe{
                    val isHighPriority = !taskeable.isHighPriority
                    onTaskHighPriorityMarked(taskeable, isHighPriority)
                    applyColorToHighPriority(buttonHighPriority, isHighPriority)
                }
                .addTo(componentDisposable)
        }
    }

    private fun applyColorToHighPriority(buttonHighPriority: IconButton, highPriority: Boolean) {
        if (highPriority){
            buttonHighPriority.setColorDrawable(Color.RED)
        }else{
            buttonHighPriority.setColorDrawable(Color.WHITE)
        }

    }

    private fun executeAnimation(view: View, isDone: Boolean) {
        val contentTextView = view.findViewById<TextView>(R.id.contentTextView)
        val text = contentTextView.text.toString()
        if(isDone) {
            applyStrikethrough(contentTextView,text,true)
        }else{
            removeStrikethrough(contentTextView,text,true)
        }
        contentTextView.invalidate()
    }

    private fun applyStrikethrough(view: TextView, text: String, animate: Boolean = false){
        val span = SpannableString(text)
        val spanStrike = StrikethroughSpan()
        if(animate) {
            ValueAnimator.ofInt(text.length)
                .apply {
                    duration = 300
                    interpolator = FastOutSlowInInterpolator()
                    addUpdateListener {
                        span.setSpan(spanStrike, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        view.text = span
                    }
                }.start()
        }else{
            span.setSpan(spanStrike, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            view.text = span
        }
    }

    private fun removeStrikethrough(view: TextView, text: String, animate: Boolean = false){
        val span = SpannableString(text)
        val spanStrike = StrikethroughSpan()
        if(animate) {
            ValueAnimator.ofInt(text.length,0)
                .apply {
                    duration = 300
                    interpolator = FastOutSlowInInterpolator()
                    addUpdateListener {
                        span.setSpan(spanStrike, 0, it.animatedValue as Int, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        view.text = span
                    }
                }.start()
        }else{
            view.text = text
        }
    }


}
