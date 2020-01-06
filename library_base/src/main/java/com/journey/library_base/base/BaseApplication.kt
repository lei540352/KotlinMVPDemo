package com.journey.library_base.base

import android.app.Application


open class BaseApplication :Application() {

    //需要返回一个单例
    init {
        instance = this
    }

    companion object{
       lateinit var instance: BaseApplication
    }

    var sIsDebug: Boolean = false

    override fun onCreate() {
        super.onCreate()
    }

}