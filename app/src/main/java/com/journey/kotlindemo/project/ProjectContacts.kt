package com.journey.kotlindemo.project

import com.journey.kotlindemo.bean.RegisterResultBean
import com.journey.library_base.activity.IBaseView
import com.journey.library_base.presenter.BasePresenter

interface ProjectContacts{

    /**
     * view 层接口
     */
    interface ProjectView :IBaseView{
        /**
         * 登陆成功
         */
        fun loginSuccess(loginbean: RegisterResultBean)

        /**
         * 登录失败
         */
        fun loginFailure(errorMsg:String)
    }

    abstract class ProjectPresenter(projectView:ProjectView):BasePresenter<ProjectView>(projectView){
        abstract fun login(username:String,password:String)
    }
}