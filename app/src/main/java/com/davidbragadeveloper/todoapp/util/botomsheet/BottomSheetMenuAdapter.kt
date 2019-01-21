package com.davidbragadeveloper.todoapp.util.botomsheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidbragadeveloper.todoapp.R
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.item_bottom_sheet_menu.view.*
import java.util.concurrent.TimeUnit

class BottomSheetMenuAdapter(
    private val items: List<BottomMenuItem>,
    private val onClick: () -> Unit
    ) : RecyclerView.Adapter<BottomSheetMenuAdapter.BottomSheetMenuViewHolder>() {

    private val compositeDisposable = CompositeDisposable()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetMenuViewHolder {
            return BottomSheetMenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bottom_sheet_menu, parent, false))
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: BottomSheetMenuViewHolder, position: Int) {
            holder.bind(items[position])
        }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        compositeDisposable.clear()
        super.onDetachedFromRecyclerView(recyclerView)
    }

        inner class BottomSheetMenuViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

            fun bind(item: BottomMenuItem) {
                with(view) {
                    bottom_menu_title.text = item.name
                    bottom_menu_icon.setImageResource(item.resId)

                    view
                        .clicks()
                        .throttleFirst(300, TimeUnit.MILLISECONDS)
                        .subscribe{
                            item.action()
                            onClick.invoke()
                        }
                        .addTo(compositeDisposable)

                }
            }
        }

    }
