package com.journey.kotlindemo.view

import android.app.Dialog
import android.content.Context
import com.journey.kotlindemo.R

object LoadingDialog {

    private var dialog :Dialog? = null

    fun show(context:Context){

        cancel()

        dialog = Dialog(context)

//        dialog?.let {
//            it.setContentView(R.layout.layout_loading)
//            it.setCancelable(false)
//            it.setCanceledOnTouchOutside(false)
//            it.show()
//        }

        dialog?.apply {
            setContentView(R.layout.layout_loading)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }?.show()
    }

    fun cancel(){
        dialog?.dismiss()
    }
}