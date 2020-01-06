package com.journey.library_base.widegt

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.journey.library_base.R

class LoadDialog(ctx: Context, cancelable: Boolean, tipMsg: String) :Dialog(ctx) {

    lateinit var rotateAnim: RotateAnimation

    companion object{
        /**
         * LoadDialog
         */
        @JvmStatic
        var loadDialog: LoadDialog? = null

        /**
         * show the dialog
         *
         * @param context
         */
        @JvmStatic
        fun show(context: Context) {
            show(context, "", true)
        }

        /**
         * show the dialog
         *
         * @param context Context
         * @param message String
         */
        @JvmStatic
        fun show(context: Context, message: String) {
            show(context, message, true)
        }

        /**
         * show the dialog
         *
         * @param context    Context
         * @param resourceId resourceId
         */
        @JvmStatic
        fun show(context: Context, resourceId: Int) {
            show(context, context.resources.getString(resourceId), true)
        }

        /**
         * show the dialog
         *
         * @param context    Context
         * @param message    String, show the message to user when isCancel is true.
         * @param cancelable boolean, true is can't dimiss，false is can dimiss
         */
        @JvmStatic
        fun show(context: Context, message: String, cancelable: Boolean) {
            if (context is Activity) {
                if (context.isFinishing) {
                    return
                }
            }
            if (loadDialog != null && loadDialog!!.isShowing) {
                return
            }
            loadDialog = message.let { LoadDialog(context, cancelable, it) }
            loadDialog!!.show()
        }

        /**
         * dismiss the dialog
         */
        @JvmStatic
        fun dismiss(context: Context) {
            try {
                if (context is Activity) {
                    if (context.isFinishing) {
                        this.loadDialog = null
                        return
                    }
                }

                if (loadDialog != null && loadDialog!!.isShowing) {
                    val loadContext = loadDialog!!.context
                    if (loadContext != null && loadContext is Activity) {
                        if (loadContext.isFinishing) {
                            loadDialog = null
                            return
                        }
                    }
                    loadDialog!!.dismiss()
                    loadDialog = null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                loadDialog = null
            }

        }
    }

    /**
     * cancelable, the dialog dimiss or undimiss flag
     */
    var cancelable: Boolean? = false
    /**
     * if the dialog don't dimiss, what is the tips.
     */
    var tipMsg: String? =  ""

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        val imageView = findViewById<View>(R.id.spinnerImageView) as ImageView
        rotateAnim = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotateAnim.setDuration(2000)
        rotateAnim.setRepeatMode(Animation.RESTART)
        rotateAnim.setRepeatCount(-1)
        rotateAnim.setInterpolator(LinearInterpolator())
        imageView?.startAnimation(rotateAnim)
    }

    /**
     * the LoadDialog constructor
     *
     * @param ctx        Context
     * @param cancelable boolean
     * @param tipMsg     String
     */
    init {
        this.cancelable = cancelable
        this.tipMsg = tipMsg
        this.context.setTheme(android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth)
        setContentView(R.layout.progress_hud)
        // 必须放在加载布局后
        setparams()
        val tv = findViewById<View>(R.id.message) as TextView
        if (!TextUtils.isEmpty(tipMsg)) {
            tv.visibility = View.VISIBLE
            tv.text = tipMsg
        }
    }

    private fun setparams() {
        cancelable?.let { this.setCancelable(it) }
        this.setCanceledOnTouchOutside(false)
        try {
            val windowManager = window!!.windowManager
            val display = windowManager.defaultDisplay
            val lp = this.window!!.attributes
            // Dialog宽度
            lp.width = (display.width * 0.7).toInt()
            val window = window
            window!!.attributes = lp
            window.decorView.background.alpha = 0
        } catch (e: Exception) {
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!cancelable!!) {
                Toast.makeText(context, tipMsg, Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}