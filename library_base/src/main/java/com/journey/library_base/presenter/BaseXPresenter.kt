package com.journey.library_base.presenter

import android.view.View
import com.journey.library_base.activity.IBaseXView
import java.lang.ref.WeakReference

/**
 * Presenter中的基类实现
 *
 * Presenter中通过IBaseXView，来实现Presenter对View的依赖，当每次对View进行调用时，
 * 先使用isViewAttach判断一下，Presenter与View之间的联系是否还在，
 * 防止内存泄漏。
 * Presenter通过View暴露的接口IBaseXView，来控制View
 * @param <V>
 */
open class BaseXPresenter<V:IBaseXView>(view: V):IBaseXpresenter{

    //如果存在主构造函数，类的属性必须存在初始化值或者用延迟加载关键字lateinit。但是 lateinit只能修饰非Kotlin基础类型，如String等
    lateinit var name : String
    //防止activity不走 onDestory()方法、 所以采用弱引用来防止内存泄露
    var mViewRef : WeakReference<V>? = null

    //init初始块在优先执行（次构造函数之前执行），如果执行过程中延迟加载的变量没有赋值，会报异常
    init {
       attachView(view)
    }

    private fun attachView(view: V) {
        mViewRef = WeakReference(view)
    }

    override fun isViewAttach(): Boolean {
        return mViewRef != null && mViewRef!!.get() != null
    }

    override fun detachView() {
        if (mViewRef != null) {
            mViewRef!!.clear()
            mViewRef = null
        }
    }

}