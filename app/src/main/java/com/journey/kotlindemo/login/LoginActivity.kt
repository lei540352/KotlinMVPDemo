package com.journey.kotlindemo.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.journey.kotlindemo.R
import com.journey.kotlindemo.activity.RegisterActivity
import com.journey.library_base.activity.BaseActivity
import com.journey.kotlindemo.bean.RegisterResultBean
import com.journey.library_base.perference.Perference
import com.journey.kotlindemo.util.toast
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginContacts.LoginPresenter>(),LoginContacts.LoginView{

    /**
     * by 委托属性 、委托代理
     */
    //sharePerference 存值
    private var islogin:Int by Perference("islogin", 1)


    companion object{//伴生对象
    val TAG = "LoginActivity"
    }

    override fun <T : Activity> getSelfActivity(): T {
        return super.getSelfActivity()
    }


    override fun layoutId(): Int {
        return R.layout.activity_login
    }

    //重新加载
    override fun onRetryBtnClick() {
    }

    override fun onBindPresenter(): LoginContacts.LoginPresenter {
        return LoginPresenter(this,this@LoginActivity)
    }


    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        //登录 按钮点击事件
        login_button.setOnClickListener(onClickListener)
        new_userBtn.setOnClickListener(onClickListener)
        login_forgetpsw.setOnClickListener(onClickListener)
    }

    override fun initData() {

        //获取 key  为islogin的值
        var IsLogin = islogin
        Log.i(TAG,"IsLogin : $IsLogin ")
        // 设置 key  为islogin的值
        islogin = 11

        Log.i(TAG,"IsLogin : $islogin ")
    }

    private val onClickListener = View.OnClickListener {
        view ->
        when(view.id){
            //登录
            R.id.login_button ->{
                //获取用户名  密码
                val userNameStr = login_name.text.toString().trim()
                val passwordStr = login_password.text.toString().trim()
                Log.i(TAG,"username : $userNameStr , password :$passwordStr")
                // 向 Presenter 层发送登录请求
                getPresenter().login(userNameStr,passwordStr)
            }

            //找回密码
            R.id.login_forgetpsw ->{
                Log.i(TAG,"找回密码")
                toast("找回密码")
            }

            //注册
            R.id.new_userBtn ->{
                val intent = Intent(this@LoginActivity,
                    RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun loginSuccess(loginbean: RegisterResultBean) {
        Log.i(TAG,"ResultBean ： $loginbean")
    }

    override fun loginFailure(errorMsg: String) {
        Log.i(TAG,"statusCode ： $errorMsg")
    }

}
