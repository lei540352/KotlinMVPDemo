package com.journey.library_base.presenter

/**
 * Presenter层的接口基类
 */
interface IBaseXpresenter {
    /**
     * 判断 presenter 是否与 view 建立联系，防止出现内存泄露状况
     *
     * @return `true`: 联系已建立<br></br>`false`: 联系已断开
     */
    fun isViewAttach(): Boolean

    /**
     * 断开 presenter 与 view 直接的联系
     */
    fun detachView()
}