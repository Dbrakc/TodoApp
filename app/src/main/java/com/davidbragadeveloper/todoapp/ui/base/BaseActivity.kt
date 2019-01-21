package com.davidbragadeveloper.todoapp.ui.base


import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.davidbragadeveloper.todoapp.R
import kotlinx.android.synthetic.main.activity_main.view.*

open class BaseActivity : AppCompatActivity() {

    protected fun setUpToolbar(homeIsEnable: Boolean){
        findViewById<Toolbar>(R.id.toolbar).apply {
            setSubtitle(null)
        }
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setHomeButtonEnabled(homeIsEnable)
        supportActionBar?.setDisplayShowHomeEnabled(homeIsEnable)
    }

}