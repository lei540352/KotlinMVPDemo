package com.journey.library_base.loadsir

import android.content.Context
import android.view.View
import com.journey.library_base.R
import com.journey.loadsir.Callback

class PlaceholderCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_placeholder
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}
