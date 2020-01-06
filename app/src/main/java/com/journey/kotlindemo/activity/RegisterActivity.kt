package com.journey.kotlindemo.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import com.journey.kotlindemo.R
import com.journey.kotlindemo.bean.RegisterResultBean
import com.journey.kotlindemo.login.LoginContacts
import com.journey.kotlindemo.login.LoginPresenter
import com.journey.kotlindemo.util.ToolBarUtil
import com.journey.library_base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity: BaseActivity<LoginContacts.LoginPresenter>(), LoginContacts.LoginView {

    companion object{//伴生对象
    val TAG = "RegisterActivity"
    }

    override fun layoutId(): Int {
        return R.layout.activity_register
    }

    override fun initData() {

    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        //返回按钮
        ToolBarUtil.Builder(findViewById(R.id.toolbar)).setType(ToolBarUtil.NORMAL_TYPE)
            .setBackRes(R.drawable.ic_back_ffffff_24dp).setBackListener {
                finish()
            }.builder()

        register_button.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener {
        view ->
        when(view.id){
            //註冊
            R.id.register_button ->{
                //获取用户名  密码
                val userNameStr = register_name.text.toString().trim()
                val passwordStr = register_password.text.toString().trim()
                val repasswordStr = register_repassword.text.toString().trim()
                Log.i(TAG,"username : $userNameStr , password :$passwordStr , repasswordStr :$repasswordStr")
                getPresenter().login(userNameStr,passwordStr)
            }
        }
    }

    override fun onRetryBtnClick() {

    }

    override fun onBindPresenter(): LoginContacts.LoginPresenter {
        return LoginPresenter(this,this@RegisterActivity)
    }

    override fun loginSuccess(loginbean: RegisterResultBean) {
        Log.i(TAG,"RegisterResultBean : $loginbean")
    }
    override fun loginFailure(errorMsg: String) {
        Log.i(TAG,"errorMsg : $errorMsg")
    }
}