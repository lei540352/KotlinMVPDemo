package com.journey.kotlindemo.login

import android.content.Context
import com.journey.kotlindemo.api.DataApi
import com.journey.kotlindemo.bean.RegisterResultBean
import com.journey.kotlindemo.network.ApiClient
import com.journey.kotlindemo.network.ApiError
import com.journey.kotlindemo.network.ApiResponse
import com.journey.kotlindemo.network.NetworkScheduler

class LoginPresenter(var loginView: LoginContacts.LoginView,val context:Context):LoginContacts.LoginPresenter(loginView){

    override fun login(username: String, password: String) {
        //显示加载条
        loginView.showLoadDialogHud()

        ApiClient.instance.getService(DataApi::class.java).loginUser(username,password)
            .compose(NetworkScheduler.compose())
//            .bindUntilEvent(this,ActivityEvent.DESTROY)
            .subscribe(object: ApiResponse<RegisterResultBean>(context){

                override fun success(data: RegisterResultBean) {
                    if (isViewAttach()) {
                        loginView.hideLoadDialogHud()
                    }
                    loginView.loginSuccess(data)
                }

                override fun failure(statusCode: Int, apiError: ApiError) {
                    // 隐藏进度条
                    if (isViewAttach()) {
                        loginView.hideLoadDialogHud()
                    }
                    loginView.loginFailure(apiError.message)
                }
            })

    }

    override fun cancel(tag: Any) {
    }

    override fun cancelAll() {
    }

}