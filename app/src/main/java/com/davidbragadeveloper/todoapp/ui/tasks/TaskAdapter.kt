package com.davidbragadeveloper.todoapp.ui.tasks

import android.animation.ValueAnimator
import android.text.Layout
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.util.DateHelper
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.longClicks
import com.jakewharton.rxbinding3.widget.checkedChanges
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.item_task.view.*
import java.util.concurrent.TimeUnit

class TaskAdapter(
    val onTaskClicked: (Task) -> Unit,
    val onTaskMarked: (Task, Boolean) -> Unit,
    val onTaskLongClicked: (Task) -> Unit
):ListAdapter<Task,TaskAdapter.TaskViewHolder>(TaskDiffUtil.getInstance()){

    private val componentDisposable = CompositeDisposable()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        componentDisposable.clear()
        super.onDetachedFromRecyclerView(recyclerView)
    }


    inner class TaskViewHolder(view: View): RecyclerView.ViewHolder(view){




        fun bind(task: Task){

            with(itemView){
                if(task.isDone){
                    applyStrikethrough(contentTextView, task.content)

                }else {
                    removeStrikethrough(contentTextView, task.content)
                }
                dateTextView.text = DateHelper.calculateTimaAgo(task.createdAt)
                isDoneCheckBox.isChecked = task.isDone

                this
                    .clicks()
                    .throttleFirst(600,TimeUnit.MILLISECONDS)
                    .subscribe{
                        onTaskClicked(task)
                    }
                    .addTo(componentDisposable)

                isDoneCheckBox
                    .clicks()
                    .throttleFirst(600,TimeUnit.MILLISECONDS)
                    .subscribe{
                        val isChecked = !task.isDone

                        onTaskMarked(task, isChecked)
                        isDoneCheckBox
                            .animate()
                            .rotationBy(360f)
                            .setDuration(600)
                            .setInterpolator(FastOutSlowInInterpolator())
                            .start()
                        executeAnimation(itemView, isChecked)
                    }
                    .addTo(componentDisposable)

                this
                    .longClicks()
                    .throttleFirst(600,TimeUnit.MILLISECONDS)
                    .subscribe{
                        onTaskLongClicked(task)
                    }
                    .addTo(componentDisposable)
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

        private fun applyStrikethrough(view:TextView, text: String, animate: Boolean = false){
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

        private fun removeStrikethrough(view:TextView, text: String, animate: Boolean = false){
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
}