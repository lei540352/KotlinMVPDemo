package com.journey.library_base.activity

//import com.kingja.loadsir.core.LoadService
import android.view.View
import com.journey.library_base.loadsir.LoadingCallback
import com.journey.library_base.presenter.IBasePresnter
import com.journey.library_base.widegt.LoadDialog
import com.journey.loadsir.Callback
import com.journey.loadsir.LoadService
import com.journey.loadsir.LoadSir

/**
 * 由Activity/Fragment实现View里方法，包含一个Presenter的引用
 * @param <P>
 */
//where  类似java 的 多重继承
abstract class BaseActivity<P : IBasePresnter> : BaseXActivity<P>(), IBaseView{

    var mLoadService : LoadService? = null

    override fun showLoading() {
        // 加载进度框
        if(mLoadService != null){
            mLoadService?.showCallback(LoadingCallback::class.java)
        }
    }

    override fun hideLoading() {
        //隐藏加载框
        if (mLoadService != null) {
            mLoadService?.showSuccess()
        }
    }

    override fun showToast(msg: String) {

    }

    override fun onRefreshEmpty() {
    }

    override fun onRefreshFailure(message: String) {
    }

    override fun onDestroy() {
        hideLoading()
        super.onDestroy()
    }

    fun setLoadSir(view: View){
        mLoadService = LoadSir.get().register(view,object : Callback.OnReloadListener{
            override fun onReload(v: View) {
                onRetryBtnClick()
            }
        })
    }

    protected abstract fun onRetryBtnClick()

    override fun showLoadDialogHud() {
        LoadDialog.show(this)
    }

    override fun hideLoadDialogHud() {
        LoadDialog.dismiss(this)
    }
}