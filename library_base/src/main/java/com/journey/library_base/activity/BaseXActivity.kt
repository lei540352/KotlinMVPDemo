package com.journey.library_base.activity

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.journey.library_base.presenter.IBaseXpresenter
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/***
 * View层基类的实现
 * View中通过IBaseXPresenter，来实现View对Presenter的依赖，同时做了内存泄漏的预防处理。
 * Activity通过getPresenter()来调用Presenter。
 * 另外，对于Fragment也可以仿照这样写。
 */
abstract class BaseXActivity<P : IBaseXpresenter> : RxAppCompatActivity(),IBaseXView{

     var mPresenter: P? = null
    /**
     * 创建presenter
     */
    abstract fun onBindPresenter() : P

    /**
     * 获取presenter 对象 在需要获取时才创建 “Presenter”，起到懒加载的作用
     */
    fun getPresenter() : P {
        if(mPresenter == null){
            mPresenter = onBindPresenter()
        }
        return mPresenter as P
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Activity> getSelfActivity(): T {
        return this as T //类似强转  (T)this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 禁止所有的activity 横屏

        if(getToolbar() != null)
        {
        }
        initView(savedInstanceState)
        initData()
    }

    abstract  fun layoutId():Int

    protected fun getToolbar(): Toolbar? {
        return null
    }
    /**
     * 初始化 view
     */
    open fun initView(savedInstanceState: Bundle?) = Unit

    open fun initData() = Unit

    override fun onDestroy() {
        super.onDestroy()
        /**
         * 在生命周期结束时，将presenter 与 view 之间的联系断开，防止出现内存泄露
         **/
        if(mPresenter != null){
            /**
             * 在生命周期结束时，将presenter 与 view 之间的联系断开，防止出现内存泄露
             **/
            mPresenter?.detachView()
        }
    }

}