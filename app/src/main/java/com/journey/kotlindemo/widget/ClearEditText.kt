package com.journey.kotlindemo.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText

/**
 * 带清除按钮的输入框
 *
 * @ClassName: ClearEditText
 */
open class ClearEditText(ctx: Context, attrs: AttributeSet,defStyle :Int):EditText(ctx,attrs,defStyle),
    View.OnFocusChangeListener,TextWatcher{

    /**
     * 删除按钮的引用
     */
    private var mClearDrawable: Drawable? = null
    /**
     * 控件是否有焦点
     */
    private var hasFoucs: Boolean = false

    init {
        (context)
    }

     override fun onFocusChange(p0: View?, p1: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(editable: Editable?) {
    }
}
