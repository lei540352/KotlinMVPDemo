package com.journey.kotlindemo.system

import com.journey.kotlindemo.R
import com.journey.library_base.fragment.BaseFragment

class SystemFragment:BaseFragment<SystemContacts.SystemPresenter>(),SystemContacts.SystemView{

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun onBindPresenter(): SystemContacts.SystemPresenter {
        return SystemPresenter(this,getSelfActivity())
    }

    override fun loadcchengg(ss: String) {
    }

    override fun fil(dd: String) {
    }

    override fun onRetryBtnClick() {
    }

}
