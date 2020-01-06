package com.journey.library_base.presenter

interface IBasePresnter:IBaseXpresenter{

    /**
     * 取消网络请求
     * @param tag 网络请求标记
     */
     fun cancel(tag: Any)

    /**
     * 取消所有的网络请求
     */
     fun cancelAll()

}