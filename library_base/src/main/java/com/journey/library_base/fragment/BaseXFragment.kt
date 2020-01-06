package com.journey.library_base.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.journey.library_base.activity.IBaseView
import com.journey.library_base.presenter.IBaseXpresenter


/***
 * View层基类的实现
 *View中通过IBaseXPresenter，来实现View对Presenter的依赖，同时做了内存泄漏的预防处理。
 * Activity通过getPresenter()来调用Presenter。
 * 另外，对于Fragment也可以仿照这样写。
 */
abstract class BaseXFragment<P : IBaseXpresenter> :Fragment(), IBaseView {

    var mPresenter: P? = null
    var layoutView : View? = null

    /**
     * 创建 presenter
     *
     * @return
     * */
    abstract fun onBindPresenter(): P

    /**
     * 获取 Presenter 对象，在需要获取时才创建 “Presenter”，起到懒加载的作用
     * */
    fun getPresenter():P{
        if(mPresenter == null){
            mPresenter = onBindPresenter()
        }
        return mPresenter as P
    }

    override fun <T : Activity> getSelfActivity(): T {
        return this as T
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutView = inflater.inflate(getLayoutId(),container,false)
        initView(savedInstanceState)
        initData()
        return layoutView
    }

    //由子类指定具体类型
    abstract fun getLayoutId(): Int

    //初始化布局
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