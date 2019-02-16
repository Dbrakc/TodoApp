package com.davidbragadeveloper.todoapp.ui.base


import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.davidbragadeveloper.todoapp.R
import com.davidbragadeveloper.todoapp.data.model.Task
import com.davidbragadeveloper.todoapp.data.model.Taskeable
import com.davidbragadeveloper.todoapp.ui.tasks.TaskDiffUtil
import com.davidbragadeveloper.todoapp.util.DateHelper
import com.davidbragadeveloper.todoapp.util.IconButton
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.longClicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.item_task.view.*
import java.util.concurrent.TimeUnit


abstract class BaseAdapter <T:Taskeable> (
    diffUtil: DiffUtil.ItemCallback<T>
    ): ListAdapter<T, BaseViewHolder<T>>(diffUtil)
{

        val componentDisposable = CompositeDisposable()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
            return object : BaseViewHolder<T>(view){
                override fun bind(taskeable: T) {
                    bindInViewHolder(taskeable, view)
                }

            }
        }
    abstract fun bindInViewHolder(taskeable: T, view: View)

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
            holder.bind(getItem(position))
        }

        override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
            componentDisposable.clear()
            super.onDetachedFromRecyclerView(recyclerView)
        }



    }