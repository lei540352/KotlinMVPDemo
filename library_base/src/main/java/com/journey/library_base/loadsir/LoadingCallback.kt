package com.journey.library_base.loadsir

import com.journey.library_base.R
import com.journey.loadsir.Callback

class LoadingCallback : Callback(){

    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }
}