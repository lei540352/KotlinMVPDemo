package com.journey.loadsir

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import java.util.*

/**
 * 存储target对应的View和一些callbcak所对应View视图的显示隐藏工作。
 * 当showCallback时，LoadLayout会将Callback对象中对应的rootView加入进LoadLayout中。
 *
 * ?.意思是这个参数可以为空,并且程序继续运行下去
 * !!.的意思是这个参数如果为空,就抛出异常
 */
class LoadLayout constructor(context: Context,onReloadListener: Callback.OnReloadListener)
    : FrameLayout(context){

    private val callbacks = HashMap<Class<out Callback>, Callback>()
    private var mContext: Context = context
    private val onReloadListener: Callback.OnReloadListener = onReloadListener
    private var preCallback: Class<out Callback>? = null
    private var curCallback: Class<out Callback>? = null
    private val CALLBACK_CUSTOM_INDEX = 1

    fun setupSuccessLayout(callback: Callback) {
        //将Callback对象放入Map中存储起来，键为Callback对应的类，值为Callback对象。
        addCallback(callback)
        //获取callback中的target转化所对应的View视图，也就是根视图
        val successView = callback.getRootView()
        //将target转化所对应的View视图GONE掉
        //在这里将rootView GONE掉是因为initCallback(builder)会重新显示defalutCallback对象
        successView.visibility = View.GONE

        //将根视图加入到LoadLayout加载布局中
        addView(successView)
        //记录当前的callback类
        curCallback = SuccessCallback::class.java
    }

    //该方法中的callback对象在Application的时候通过addCallback方法加入进来的
    fun setupCallback(callback: Callback) {
        //克隆出来了一个callback 对象
        val cloneCallback = callback.copy()
        //将上下文和重新加载监听器设置到cloneCallback对象中
        cloneCallback?.setCallback(null, mContext, onReloadListener)
        //将cloneCallback的类型作为key，将cloneCallback对象作为值存入Map中，放入LoadLayout对象中保存
        cloneCallback?.let { addCallback(it) }
    }

    //该方法会将callback对象存入callbacks map集合当中
    fun addCallback(callback: Callback) {
        if (!callbacks.containsKey(callback.javaClass)) {
            callbacks[callback.javaClass] = callback
        }
    }

    //显示callback
    fun showCallback(callback: Class<out Callback>) {
        checkCallbackExist(callback)
        if (LoadSirUtil.isMainThread) {
            showCallbackView(callback)
        } else {
            postToMainThread(callback)
        }
    }

    fun getCurrentCallback(): Class<out Callback>? {
        return curCallback
    }

    private fun postToMainThread(status: Class<out Callback>) {
        post { showCallbackView(status) }
    }

    //前一个callback不为null，且前一个callback与传入的status callback相等，则退出显示callback逻辑
    private fun showCallbackView(status: Class<out Callback>) {
        if (preCallback != null) {
            if (preCallback == status) {
                return
            }
            //获取前一个callback对象，使callback与loadLayout分离
            callbacks[preCallback!!]!!.onDetach()
        }
        if (childCount > 1) {
            //删除loadLayout 容器中的第一个callback中的rootView
            removeViewAt(CALLBACK_CUSTOM_INDEX)
        }
        //遍历callbacks map集合中的key
        for (key in callbacks.keys) {
            Log.i("TTT","key : "+key)
            Log.i("TTT","status : "+status)
            //如果key等于传入的callback类型，取出该类型对应的对象
            if (key == status) {
                val successCallback = callbacks[SuccessCallback::class.java] as SuccessCallback?
                if (key == SuccessCallback::class.java) {
                    //拿到rootView 将rootView设置为显示状态
                    successCallback!!.show()
                } else {
                    //callbacks.get(key).getSuccessVisible() 获取该Callback是否是显示的状态
                    //将 successCallback 显示或者隐藏
                    successCallback!!.showWithCallback(callbacks[key]!!.successViewVisible)

                    //获取普通的callback rootView
                    val rootView = callbacks[key]!!.getRootView()
                    //将rootView加入加载布局中
                    addView(rootView)
                    //将callback与loadLayout绑定
                    callbacks[key]!!.onAttach(mContext, rootView)
                }
                //前一个callback对象赋值为该callback类型
                preCallback = status
            }
        }
        //当前callback对象赋值为该callback类型
        curCallback = status
    }

    fun setCallBack(callback: Class<out Callback>, transport: Transport?) {
        if (transport == null) {
            return
        }
        checkCallbackExist(callback)
        //callbacks.get(callback).obtainRootView() 返回rootView
        callbacks[callback]!!.obtainRootView()?.let { transport.order(mContext, it) }
    }

    private fun checkCallbackExist(callback: Class<out Callback>) {
        Log.i("simpleName==",""+callback
            .simpleName)
//
//        require(callbacks.containsKey(callback)) {
//            String.format(
//                "The Callback (%s) is nonexistent.", callback
//                    .simpleName
//            )
//        }
    }
}