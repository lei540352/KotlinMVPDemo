package com.journey.loadsir

import android.util.Log
import java.util.ArrayList
import androidx.annotation.NonNull

/**
 * 内部类
 * 1.kotlin默认的内部类是静态内部类，不能持有外部类的状态（属性、方法等）
 * 2.给内部类加上inner关键词之后，就会变成非静态内部类，可以访问外部类的属性和方法
 * 3.非静态内部类想访问外部类的属性，可以使用 this@外部类名.外部类属性名 的形式访问
 * 4.非静态内部类可以访问到外部静态内部类的方法和属性，静态内部类访问不到外部所有的属性和方法
 */
class LoadSir {

    /**
     * 这个类是用来初始化的，设置callback对象。采用构建者模式，这段代码比较重要。
     * register()方法很重要，在这个方法中需要设置目标对象，
     * 以后会获取该目标的父容器，最后会将LoadLayout布局加入到该目标对象的父容器中。
     */
    var builder: Builder

    companion object {
        private var instance: LoadSir? = null
            get() {
                if (field == null) {
                    field = LoadSir()
                }
                return field
            }

        @Synchronized
        fun get(): LoadSir {
            return instance!!
        }

        @JvmStatic
        fun beginBuilder(): Builder {
            return Builder()
        }
    }

    constructor(builder: Builder) {
        this.builder = builder
    }

    constructor(){
        this.builder = Builder()
    }

    /**
     *target 目标
     * onReloadListener 重载监听
     */
    fun register(target: Any, onReloadListener: Callback.OnReloadListener): LoadService {
        //TargetContext 记录target所对应View在父容器中的位置等信息。
        val targetContext = LoadSirUtil.getTargetContext(target)
        return LoadService(targetContext, onReloadListener, builder)
    }

    //默认静态类
    class Builder {
        private val callbacks = ArrayList<Callback>()
        private var defaultCallback: Class<out Callback>? = null

        fun addCallback(@NonNull callback: Callback): Builder {
            callbacks.add(callback)
            return this
        }

        fun setDefaultCallback(@NonNull defaultCallback: Class<out Callback>): Builder {
            this.defaultCallback = defaultCallback
            return this
        }

        internal fun getCallbacks(): List<Callback> {
            return callbacks
        }

        internal fun getDefaultCallback(): Class<out Callback>? {
            return defaultCallback
        }

        fun commit() {
            LoadSir.get().builder = this
        }

        fun build(): LoadSir {
            return LoadSir(this)
        }
    }
}
