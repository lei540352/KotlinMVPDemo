package com.journey.kotlindemo.system

import com.journey.library_base.activity.IBaseView
import com.journey.library_base.presenter.BasePresenter

interface SystemContacts {

    /**
     * view 层接口
     */
    interface SystemView :IBaseView{
        fun loadcchengg(ss:String)
        fun fil(dd:String)
    }

    /**
     * 业务层接口
     */
    abstract class SystemPresenter(systemView:SystemView):BasePresenter<SystemView>(systemView){
        abstract fun getHome(uid:String,flag:String)
    }
}