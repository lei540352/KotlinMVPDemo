package com.journey.library_base.loadsir

import com.journey.library_base.R
import com.journey.loadsir.Callback

class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}