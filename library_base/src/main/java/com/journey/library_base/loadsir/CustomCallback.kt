package com.journey.library_base.loadsir

import android.content.Context
import android.view.View
import android.widget.Toast
import com.journey.library_base.R
import com.journey.loadsir.Callback

class CustomCallback : Callback() {

    protected override fun onCreateView(): Int {
        return R.layout.layout_custom
    }

    protected override fun onReloadEvent(context: Context?, view: View?): Boolean {
        Toast.makeText(context?.applicationContext, "Hello buddy, how r u! :p", Toast.LENGTH_SHORT)
            .show()
        view?.findViewById<View>(R.id.iv_gift)?.setOnClickListener {
            Toast.makeText(
                context?.applicationContext,
                "It's your gift! :p",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }
}