package com.journey.library_base.loadsir

import android.content.Context
import android.view.View
import android.widget.Toast
import com.journey.library_base.R
import com.journey.loadsir.Callback

class TimeoutCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_timeout
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        Toast.makeText(
            context?.applicationContext,
            "Connecting to the network again!",
            Toast.LENGTH_SHORT
        ).show()
        return false
    }

}