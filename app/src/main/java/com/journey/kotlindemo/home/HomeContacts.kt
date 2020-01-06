package com.journey.kotlindemo.home

import com.journey.kotlindemo.bean.RegisterResultBean
import com.journey.library_base.activity.IBaseView
import com.journey.library_base.presenter.BasePresenter

interface HomeContacts {
    /**
     * view 层接口
     */
    interface HomeView : IBaseView {
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
    abstract class HomePresenter(view: HomeView) : BasePresenter<HomeView>(view) {

        abstract fun login(username: String, password: String)
    }
}