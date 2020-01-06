package com.journey.kotlindemo.project

import com.journey.kotlindemo.R
import com.journey.kotlindemo.bean.RegisterResultBean
import com.journey.library_base.fragment.BaseFragment

class ProjectFragment : BaseFragment<ProjectContacts.ProjectPresenter>(),ProjectContacts.ProjectView{
    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun onBindPresenter(): ProjectContacts.ProjectPresenter {
        return ProjectPresenter(this,getSelfActivity())
    }

    override fun loginSuccess(loginbean: RegisterResultBean) {
    }

    override fun loginFailure(errorMsg: String) {
    }

    override fun onRetryBtnClick() {
    }

}