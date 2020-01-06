package com.journey.library_base.fragment

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.journey.library_base.activity.IBaseView
import com.journey.library_base.loadsir.EmptyCallback
import com.journey.library_base.loadsir.ErrorCallback
import com.journey.library_base.loadsir.LoadingCallback
import com.journey.library_base.presenter.IBasePresnter
import com.journey.library_base.utils.ToastUtil
import com.journey.library_base.widegt.LoadDialog
import com.journey.loadsir.Callback
import com.journey.loadsir.LoadService
import com.journey.loadsir.LoadSir

abstract class BaseFragment<P :IBasePresnter> :BaseXFragment<P>() ,IBaseView{

    var mLoadService : LoadService? = null

    override fun showLoading() {
        if (mLoadService != null){
            mLoadService?.showCallback(LoadingCallback::class.java)
        }
    }

    override fun hideLoading() {
        //隐藏加载框
        if (mLoadService != null) {
            mLoadService?.showSuccess()
        }
    }

    override fun onRefreshEmpty() {
        if (mLoadService != null) {
            mLoadService?.showCallback(EmptyCallback::class.java)
        }
    }

    override fun onRefreshFailure(message: String) {
        if (mLoadService != null) {
            mLoadService?.showCallback(ErrorCallback::class.java)
        }
    }

    override fun showToast(msg: String) {
        ToastUtil.show(activity, msg)
    }

    override fun onDestroy() {
        hideLoading()
        super.onDestroy()
    }

    fun setLoadSir(view: View) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.get().register(view, object : Callback.OnReloadListener{
            override fun onReload(v: View) {
                onRetryBtnClick()
            }
        })
    }

    protected abstract fun onRetryBtnClick()

    override fun showLoadDialogHud() {
        LoadDialog.show(activity as Context)
    }

    override fun hideLoadDialogHud() {
        LoadDialog.dismiss(activity as Context)
    }
}