package com.journey.loadsir

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

//将target目标所对应的View，加入到LoadLayout布局中。
//LoadLayout中的setupSuccessLayout()方法会将SuccessCallback对象的rootView设置到loadLayout对象中。
/**
 *
 */
class LoadService(
    targetContext: TargetContext,
    onReloadListener: Callback.OnReloadListener,
    builder: LoadSir.Builder
) {

    val loadLayout: LoadLayout

    init {
        //上下文
        val context = targetContext.context
        //target转化所对应的View
        val oldContent = targetContext.oldContent
        //target转化所对应的View布局参数
        val oldLayoutParams = oldContent.layoutParams
        // 创建一个加载布局
        loadLayout = LoadLayout(context, onReloadListener)
        // SuccessCallback(oldContent, context, onReloadListener) 将target转化所对应的视图，
        // 上下文，重加载监听，加入Callback类中，创建一个SuccessCallback对象出来
        //setupSuccessLayout() 会将callback中rootView加入到loadLayout布局中
        loadLayout.setupSuccessLayout(
            SuccessCallback(
                oldContent, context,
                onReloadListener
            )
        )
        //获取target所对应的父容器，将加载布局对象loadLayout加入target 所在父容器的索引值位置，该
        // 加载布局对象loadLayout的布局参数也是target所对应View的布局参数
        //说白了，就是将target所对应的View对象转化为了loadLayout对象了，并且加入到了target所对应父容器中。
        //getTargetContext(Object target)方法中会将父容器中的target所对应的视图移除。
        targetContext.parentView
                .addView(loadLayout, targetContext.childIndex, oldLayoutParams)
        //initCallback(builder)会重新显示defalutCallback对象
        initCallback(builder)
    }

    private fun initCallback(builder: LoadSir.Builder) {

        //callbacks是用来存储各种ErrorCallback，EmptyCallback对象的集合
        val callbacks = builder.getCallbacks()

        //defalutCallback 用来存储LoadingCallback.class类。
        val defalutCallback = builder.getDefaultCallback()

        //`true` if the collection is not empty.如果集合不为空，则为true。
        if (callbacks.isNotEmpty()) {
            for (callback in callbacks) {
                //将callback放入loadLayout布局容器中的callbacks Map集合中存储起来，
                // 该map集合中key是callbacks类型，值是callback对象
                loadLayout.setupCallback(callback)
            }
        }
        Log.i("TTT","defalutCallback : "+defalutCallback)
        //如果默认的callback不为null，则将该callback显示出来
        if (defalutCallback != null) {
            loadLayout.showCallback(defalutCallback)
        }
    }

    fun showSuccess() {
        loadLayout.showCallback(SuccessCallback::class.java)
    }

    fun showCallback(callback: Class<out Callback>) {
        loadLayout.showCallback(callback)
    }

    fun getCurrentCallback(): Class<out Callback> {
        return loadLayout.getCurrentCallback()!!
    }

    /**
     * obtain rootView if you want keep the toolbar in Fragment
     * @since 1.2.2
     */
    @Deprecated("")
    fun getTitleLoadLayout(context: Context, rootView: ViewGroup, titleView: View): LinearLayout {
        val newRootView = LinearLayout(context)
        newRootView.orientation = LinearLayout.VERTICAL
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        newRootView.layoutParams = layoutParams
        rootView.removeView(titleView)
        newRootView.addView(titleView)
        newRootView.addView(loadLayout, layoutParams)
        return newRootView
    }

    /**
     * modify the callback dynamically
     *
     * @param callback  which callback you want modify(layout, event)
     * @param transport a interface include modify logic
     * @since 1.2.2
     */
    fun setCallBack(callback: Class<out Callback>, transport: Transport): LoadService {
        loadLayout.setCallBack(callback, transport)
        return this
    }
}
