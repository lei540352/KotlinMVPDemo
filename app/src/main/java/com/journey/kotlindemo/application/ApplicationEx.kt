package com.journey.kotlindemo.application

import com.journey.kotlindemo.BuildConfig
import com.journey.kotlindemo.network.ApiClient
import com.journey.library_base.base.BaseApplication
import com.journey.library_base.loadsir.*
import com.journey.library_base.utils.Logger
import com.journey.loadsir.LoadSir


class ApplicationEx : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        Logger.sEnabled = true
        //初始化 retrofit 基类 API 实例对象
        ApiClient.instance.createRetrofit()
        sIsDebug = BuildConfig.DEBUG

        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())//添加各种状态页
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .addCallback(TimeoutCallback())
            .addCallback(CustomCallback())
            .addCallback(ErrorCallback())
            .addCallback(AnimateCallback())
            .addCallback(LottieLoadingCallback())
            .addCallback(PlaceholderCallback())
            .setDefaultCallback(LoadingCallback::class.java)//设置默认状态页
            .commit()
    }
}