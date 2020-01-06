package com.journey.kotlindemo.me

import com.journey.kotlindemo.R
import com.journey.kotlindemo.bean.RegisterResultBean
import com.journey.library_base.fragment.BaseFragment

class MyFragment :BaseFragment<MyContacts.MyPresenter>(),MyContacts.MyView{

    override fun onBindPresenter(): MyContacts.MyPresenter {
        return MyPresenter(this,getSelfActivity())
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun onRetryBtnClick() {
    }

    override fun loginSuccess(loginbean: RegisterResultBean) {
    }

    override fun loginFailure(errorMsg: String) {
    }
}