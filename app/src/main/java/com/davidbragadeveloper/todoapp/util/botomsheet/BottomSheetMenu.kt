package com.davidbragadeveloper.todoapp.util.botomsheet

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davidbragadeveloper.todoapp.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_menu.view.*

class BottomSheetMenu(private val context: Context,
                      private val items: List<BottomMenuItem>) {

    private val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(context, R.style.dialog)

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_menu, null)
        bottomSheetDialog.setContentView(view)

        with(view) {
            document_sign_type_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            document_sign_type_recycler.adapter = BottomSheetMenuAdapter(
                items = items,
                onClick = {bottomSheetDialog.dismiss()}
            )
        }
    }

    fun show() {
        bottomSheetDialog.show()
    }
}