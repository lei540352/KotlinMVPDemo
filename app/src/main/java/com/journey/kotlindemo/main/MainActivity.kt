package com.journey.kotlindemo.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.journey.kotlindemo.R
import com.journey.library_base.activity.BaseActivity
import com.journey.kotlindemo.bean.RegisterResultBean
import com.journey.kotlindemo.home.HomeFragment
import com.journey.kotlindemo.login.LoginContacts
import com.journey.kotlindemo.login.LoginPresenter
import com.journey.kotlindemo.util.ToolBarUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<LoginContacts.LoginPresenter>(), LoginContacts.LoginView {

    override fun onBindPresenter(): LoginContacts.LoginPresenter {
       return LoginPresenter(this,this@MainActivity)
    }

    override fun loginSuccess(loginbean: RegisterResultBean) {
    }

    override fun loginFailure(errorMsg: String) {
    }

    override fun onRetryBtnClick() {
    }

    private lateinit var mTempFragment:Fragment

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        initBottomNavigation()
    }

    override fun initData() {
        setSupportActionBar(
            ToolBarUtil.Builder(findViewById(R.id.toolbar))
                .setType(ToolBarUtil.NORMAL_TYPE)
                .setSearchTitleRes(R.string.tab_1)
                .builder()
        )
        //初始化默认页面
        mTempFragment = MainFragmentManager.instance.getHomeFragment()
        supportFragmentManager.beginTransaction().add(R.id.frame_container,mTempFragment as HomeFragment)
            .show(mTempFragment as HomeFragment)
            .commit()
    }
    /**
     * 初始化底部布局
     */
    private fun initBottomNavigation(){
        bn_bar.let {
            it.setBarBackgroundColor(R.color.c_ff)
                .addItem(
                    BottomNavigationItem(R.mipmap.ic_launcher,R.string.tab_1)
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColor(R.color.color_hong)
                        .setInActiveColor(R.color.black30)
                )
                .addItem(
                    BottomNavigationItem(R.mipmap.ic_launcher,R.string.tab_2)
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColor(R.color.color_hong)
                        .setInActiveColor(R.color.black30)
                )
                .addItem(
                    BottomNavigationItem(R.mipmap.ic_launcher,R.string.tab_3)
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColor(R.color.color_hong)
                        .setInActiveColor(R.color.black30)
                )
                .addItem(
                    BottomNavigationItem(R.mipmap.ic_launcher,R.string.tab_4)
                        .setInactiveIconResource(R.mipmap.ic_launcher)
                        .setActiveColor(R.color.color_hong)
                        .setInActiveColor(R.color.black30)
                )
                .initialise()

            it.setMode(BottomNavigationBar.MODE_FIXED)

            it.setTabSelectedListener(object :BottomNavigationBar.OnTabSelectedListener{

                override fun onTabReselected(position: Int) {
                }

                override fun onTabUnselected(position: Int) {
                }

                override fun onTabSelected(position: Int) {
                    switchFragment(position)
                }

            })
        }
    }

    fun switchFragment(index :Int){
        var fragment : Fragment? = null
        when(index){
            0 -> {
                fragment = MainFragmentManager.instance.getHomeFragment()
            }

            1 ->{
                fragment = MainFragmentManager.instance.getSystemFragment()
            }

            2 ->{
                fragment = MainFragmentManager.instance.getProjectFragment()
            }

            3 ->{
                fragment = MainFragmentManager.instance.getMyFragment()
            }
        }

        fragment?.let {
            switchFragment(it)
        }
    }

    //同名函数 参数不同
    private fun switchFragment(fragment: Fragment){
        //判空
        if(fragment == mTempFragment){
            return
        }

        //如果片段当前已添加到其活动中，则返回true。
        if(fragment.isAdded){
            supportFragmentManager.beginTransaction().hide(mTempFragment).show(fragment).commit()
        }else{
            supportFragmentManager.beginTransaction().hide(mTempFragment).add(R.id.frame_container,fragment).commit()
        }
        mTempFragment = fragment
    }

    //销毁页面
    override fun onDestroy() {
        super.onDestroy()
        MainFragmentManager.instance.destroy()
    }
}
