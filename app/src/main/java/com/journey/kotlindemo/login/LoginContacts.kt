package com.journey.kotlindemo.login

import com.journey.kotlindemo.bean.RegisterResultBean
import com.journey.library_base.activity.IBaseView
import com.journey.library_base.presenter.BasePresenter

interface LoginContacts {
    /**
     * view 层接口
     */
    interface LoginView : IBaseView {
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
    abstract class LoginPresenter(view: LoginView) : BasePresenter<LoginView>(view) {

        abstract fun login(username: String, password: String)
    }

}