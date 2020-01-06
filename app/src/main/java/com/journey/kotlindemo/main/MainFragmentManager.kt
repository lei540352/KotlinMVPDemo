package com.journey.kotlindemo.main

import androidx.fragment.app.Fragment
import com.journey.kotlindemo.home.HomeFragment
import com.journey.kotlindemo.me.MyFragment
import com.journey.kotlindemo.project.ProjectFragment
import com.journey.kotlindemo.system.SystemFragment
import java.lang.ref.WeakReference

class MainFragmentManager private constructor(){

    /**
     * companion object
     * 使用 object 关键字来声明一个对象 获得一个单例
     * 类内部的对象声明可以用 companion 关键字标记，可以直接通过外部类访问到对象的内部元素。
     */
    companion object{
        var instance = Singleton.singleton
    }

    private object Singleton{
        val singleton = MainFragmentManager()
    }

    private var map : MutableMap<String,WeakReference<Fragment>>? = null


    private fun getFragmentMap():MutableMap<String,WeakReference<Fragment>>{
        if(map == null){
            map = hashMapOf()
        }
        return map as MutableMap<String, WeakReference<Fragment>>
    }

    //首页
    fun getHomeFragment(): HomeFragment {
        if(getFragmentMap()[HomeFragment::class.java.simpleName] == null){
            val fragment = HomeFragment()
            getFragmentMap().put(HomeFragment::class.java.simpleName, WeakReference(fragment))
        }
        return getFragmentMap()[HomeFragment::class.java.simpleName]?.get() as HomeFragment
    }

    //体系
    fun getSystemFragment(): SystemFragment {
        if(getFragmentMap()[SystemFragment::class.java.simpleName] == null){
            val fragment = SystemFragment()
            getFragmentMap().put(SystemFragment::class.java.simpleName, WeakReference(fragment))
        }
        return getFragmentMap()[SystemFragment::class.java.simpleName]?.get() as SystemFragment
    }

    //项目
    fun getProjectFragment(): ProjectFragment {
        if(getFragmentMap()[ProjectFragment::class.java.simpleName] == null){
            val fragment = ProjectFragment()
            getFragmentMap().put(ProjectFragment::class.java.simpleName, WeakReference(fragment))
        }
        return getFragmentMap()[ProjectFragment::class.java.simpleName]?.get() as ProjectFragment
    }

    //我的
    fun getMyFragment(): MyFragment {
        if(getFragmentMap()[MyFragment::class.java.simpleName] == null){
            val fragment = MyFragment()
            getFragmentMap().put(MyFragment::class.java.simpleName, WeakReference(fragment))
        }
        return getFragmentMap()[MyFragment::class.java.simpleName]?.get() as MyFragment
    }

    fun destroy(){
        map?.clear()
    }

    fun destroy(fragment: Fragment?){
        if(map == null || fragment == null){
            return
        }
        getFragmentMap().remove(fragment.javaClass.simpleName)
    }
}