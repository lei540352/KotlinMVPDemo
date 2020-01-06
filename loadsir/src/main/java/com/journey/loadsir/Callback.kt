package com.journey.loadsir

import android.content.Context
import android.view.View
import java.io.*

/**
 * 这个类主要就是存储状态视图的，经常需要从该类中获取viewRoot。
 * 当想自定义Callback类的时候，需要继承Callback类。实现onCreateView()方法，返回布局视图ID，
 * 当调用callback的getRootView()方法时候，
 * 如果构造callback对象时候没有传入taget值，那么返回的viewRoot
 * 则是onCreateView()方法返回的布局ID所对应的视图。
 */
abstract class Callback() : Serializable {
    private var rootView: View? = null
    private var context: Context? = null
    private var onReloadListener: OnReloadListener? = null

    var successViewVisible: Boolean = false

    constructor(view: View, context: Context, onReloadListener: OnReloadListener) : this() {
        this.rootView = view
        this.context = context
        this.onReloadListener = onReloadListener
    }

    fun setCallback(view: View?, context: Context, onReloadListener: OnReloadListener): Callback {
        this.rootView = view
        this.context = context
        this.onReloadListener = onReloadListener
        return this
    }

    //返回目标视图
    fun getRootView(): View {
        //获取视图ID
        val resId = onCreateView()
        //如果视图ID为null，且target转化后所对应的view就是为rootView，该view如果不为null的话，就将target转化所对应的rootView返回
        if (resId == 0 && rootView != null) {
            return rootView as View
        }

        if (onBuildView(context) != null) {
            rootView = onBuildView(context)
        }

        //如果target转化所对应的View为null，那么就使用onCreateView()返回的视图ID转化为rootView返回。
        if (rootView == null) {
            rootView = View.inflate(context, onCreateView(), null)
        }
        //为target转化所对应的View或者视图ID所对应的View设置点击事件，
        // onReloadEvent(context, rootView)判断是否执行该点击事件，false为执行，true为不执行。
        // onReloadListener.onReload(v) 点击事件的执行逻辑写在onRload方法中，该方法在设置convert时可以具体实现。
        rootView!!.setOnClickListener(View.OnClickListener { v ->
            if (onReloadEvent(context, rootView)) {
                return@OnClickListener
            }
            if (onReloadListener != null) {
                onReloadListener!!.onReload(v)
            }
        })
        onViewCreate(context, rootView!!)
        //返回目标视图
        return rootView as View
    }

    protected fun onBuildView(context: Context?): View? {
        return null
    }

    @Deprecated("Use {@link #onReloadEvent(Context context, View view)} instead.")
    protected fun onRetry(context: Context, view: View): Boolean {
        return false
    }

    protected open fun onReloadEvent(context: Context?, view: View?): Boolean {
        return false
    }

    //copy方法是将该对象读取出来，并返回。
    fun copy(): Callback? {
        val bao = ByteArrayOutputStream()
        val oos: ObjectOutputStream
        var obj: Any? = null
        try {
            oos = ObjectOutputStream(bao)
            oos.writeObject(this)
            oos.close()
            val bis = ByteArrayInputStream(bao.toByteArray())
            val ois = ObjectInputStream(bis)
            obj = ois.readObject()
            ois.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return obj as Callback?
    }

    /**
     * 获取目标视图
     * @since 1.2.2
     */
    fun obtainRootView(): View? {
        if (rootView == null) {
            rootView = View.inflate(context, onCreateView(), null)
        }
        return rootView
    }

    interface OnReloadListener : Serializable {
        fun onReload(v: View)
    }

    //返回视图的ID
    protected abstract fun onCreateView(): Int

    /**
     * Called immediately after [.onCreateView]
     * @since 1.2.2
     */
    protected open fun onViewCreate(context: Context?, view: View) {}

    /**
     * Called when the rootView of Callback is attached to its LoadLayout.
     *
     * @since 1.2.2
     */
    open fun onAttach(context: Context, view: View) {}

    /**
     * Called when the rootView of Callback is removed from its LoadLayout.
     *
     * @since 1.2.2
     */
    open fun onDetach() {}

}
