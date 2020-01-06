package com.journey.library_base.perference

import android.content.Context
import android.content.SharedPreferences
import com.journey.library_base.base.BaseApplication
import kotlin.reflect.KProperty

class Perference<T>(val name:String,private val default: T){

    private val prefs:SharedPreferences by lazy {
        BaseApplication.instance.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    operator  fun getValue(thisRef:Any?,property:KProperty<*>):T{
        return getSharedPreferences(name,default)
    }

    operator fun setValue(thisRef: Any?,property: KProperty<*>,value:T){
        putSharedPreferences(name,value)
//
    }

    //存值
    private fun putSharedPreferences(name:String,value:T) = with(prefs.edit()){
        when(value){
            is Long -> putLong(name,value)
            is String -> putString(name,value)
            is Int ->putInt(name,value)
            is Float ->putFloat(name,value)
            is Boolean ->putBoolean(name,value)
            else -> throw IllegalStateException("非法传值")
        }
    }.apply()//commit方法和apply方法都表示提交修改

    /**
     * 取值
     */
    private fun getSharedPreferences(name:String,default:T):T = with(prefs){
        val result =  when(default){
            is Long -> getLong(name,default)
            is String -> getString(name,default)?:""
            is Int -> getInt(name,default)
            is Float -> getFloat(name,default)
            is Boolean -> getBoolean(name,default)
            else -> throw IllegalStateException("非法取值")
        }
        return result as T
    }
}