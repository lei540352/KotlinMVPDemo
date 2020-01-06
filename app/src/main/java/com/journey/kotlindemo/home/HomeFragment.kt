package com.journey.kotlindemo.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.journey.kotlindemo.R
import com.journey.kotlindemo.bean.RegisterResultBean
import com.journey.kotlindemo.login.LoginActivity
import com.journey.library_base.fragment.BaseFragment
import kotlinx.android.synthetic.main.activity_home.view.*

class HomeFragment: BaseFragment<HomeContacts.HomePresenter>(),HomeContacts.HomeView{

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun onBindPresenter(): HomeContacts.HomePresenter {
        return HomePresenter(this,getSelfActivity())
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {

        layoutView?.login_layout?.setOnClickListener{
            var intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRetryBtnClick() {
    }

    override fun loginSuccess(loginbean: RegisterResultBean) {
    }

    override fun loginFailure(errorMsg: String) {
    }
}