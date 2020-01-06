package com.journey.loadsir

import android.app.Activity
import android.content.Context
import android.os.Looper
import android.view.View
import android.view.ViewGroup

object LoadSirUtil {
    /**
     * 这个类是用来创建target对象的上下文，在这个类中有一行代码很重要，
     * 通过target获取它的父容器，并且将target删除，然后创建一个TargetContext对象。
     */
    val isMainThread: Boolean
        get() = Looper.myLooper() == Looper.getMainLooper()

    fun getTargetContext(target: Any): TargetContext {
        val contentParent: ViewGroup?
        val context: Context
        if (target is Activity) {
            //target 如果是 Activity对象
            context = target
            contentParent = target.findViewById<View>(android.R.id.content) as ViewGroup
        } else if (target is View) {
            //如果target是View，就获取该View的父类容器
            contentParent = target.parent as ViewGroup
            context = target.context
        } else {
            throw IllegalArgumentException("The target must be within Activity, Fragment, View.")
    }
        //如果有父容器，则获取父容器中的子视图个数，如果没有父容器，则父容器中子视图个数为0.
        //if(contentParent != null) contentParent.childCount else 0
        val childCount = contentParent?.childCount ?: 0
        //传入的target转化后对应的视图
        val oldContent: View
        // 父容器中，记录target转化后所对应视图的索引值
        var childIndex = 0
        if (target is View) {//如果target输入View视图
            oldContent = target
            for (i in 0 until childCount) {
                if (contentParent.getChildAt(i) === oldContent) {
                    childIndex = i
                    break
                }
            }
        } else {
            //如果target属于activity
            // 父容器不为空，则传入的target所对应的视图为父容器中第一个子视图，否则target对应的视图就为null。
            oldContent = contentParent?.getChildAt(0)
        }
        //如果target对应的视图为null，则抛出异常
        requireNotNull(oldContent) {
            String.format(
                "enexpected error when register LoadSir in %s",
                target.javaClass.simpleName
            )
        }
        //如果父容器不为null，则将父容器中的target所对应的视图移除，删除的视图稍后会加入到LoadLayout布局中。
        contentParent?.removeView(oldContent)

        //将context，父容器，target所对应的视图，target所对应视图在父容器中的索引值，
        // 构造一个TargetContext对象，并返回。
        return TargetContext(context, contentParent, oldContent, childIndex)
    }
}