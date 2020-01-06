package com.journey.library_base.utils

import android.content.Context
import android.text.TextUtils
import android.widget.Toast

class ToastUtil {
    companion object{

        @JvmStatic
        private var mToast: Toast? = null

        @JvmStatic
        fun show(context: Context?, msg: String) {
            try {
                if (context != null && !TextUtils.isEmpty(msg)) {
                    if (mToast != null) {
                        mToast!!.cancel()
                    }
                    mToast = Toast.makeText(context, "", Toast.LENGTH_LONG)
                    mToast!!.setText(msg)
                    mToast!!.show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}
