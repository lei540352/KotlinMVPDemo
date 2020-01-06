package com.journey.library_base.presenter

import com.journey.library_base.activity.IBaseView

/***
 *  Presenter 是View和Model的桥梁，它从Model层检索数据后，返回给View层
 *  1、UI实现View方法，引用Presenter
 *  2、Presenter调用Model，走Model具体逻辑
 *  View --1--> Presenter --2--> Model
 *
 *  3、Model逻辑实现，回调Presenter方法
 *  4、Presenter回调View，即回到UI，回调View方法
 *  Model --3-->Presenter--4--->View
 *
 * @param <V>
 */
abstract class BasePresenter<V :IBaseView>(v: V) :BaseXPresenter<V>(v), IBasePresnter {

}