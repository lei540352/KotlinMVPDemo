package com.journey.library_base.activity


/**
 * 常用到的方法
 * 处理业务需要哪些方法
 */
interface IBaseView :IBaseXView {


    /**
     * 显示正在加载的view
     */
    fun showLoading()

    /**
     * 关闭正在加载的view
     */
    fun hideLoading()

    /**
     * 显示正在加载弹出框
     */
    fun showLoadDialogHud()

    /**
     * 关闭正在加载弹出框
     */
    fun hideLoadDialogHud()


    /**
     *没有內容
     */
    fun onRefreshEmpty()

    /**
     * 加载失败
     * @param message
     */
    fun onRefreshFailure(message:String)

    /**
     * 显示提示
     * @param msg
     */
    fun showToast(msg : String)
}