package com.journey.loadsir

import android.content.Context
import android.util.Log
import android.view.View

class SuccessCallback(view: View, context: Context, onReloadListener: OnReloadListener) :
    Callback(view, context, onReloadListener) {

    override fun onCreateView(): Int {
        return 0
    }

    fun hide() {
        obtainRootView()?.setVisibility(View.INVISIBLE)
    }

    fun show() {
        obtainRootView()?.setVisibility(View.VISIBLE)
    }

    fun showWithCallback(successVisible: Boolean) {

        Log.i("TTT","successVisible : "+successVisible)
        obtainRootView()?.setVisibility(if (successVisible) View.VISIBLE else View.INVISIBLE)
    }

}
