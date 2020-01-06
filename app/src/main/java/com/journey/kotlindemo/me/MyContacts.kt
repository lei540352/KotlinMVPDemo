package com.journey.kotlindemo.me

import com.journey.kotlindemo.bean.RegisterResultBean
import com.journey.library_base.activity.IBaseView
import com.journey.library_base.presenter.BasePresenter

interface MyContacts {

    /**
     * view 层接口
     */
    interface MyView : IBaseView {
        /**
         * 登陆成功
         */
        fun loginSuccess(loginbean: RegisterResultBean)

        /**
         * 登录失败
         */
        fun loginFailure(errorMsg:String)
    }

    /**
     * presenter 层接口
     */
    abstract class MyPresenter(view: MyView) : BasePresenter<MyView>(view) {

        abstract fun login(username: String, password: String)
    }
}