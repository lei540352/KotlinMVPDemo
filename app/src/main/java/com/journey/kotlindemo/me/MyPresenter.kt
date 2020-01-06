package com.journey.kotlindemo.me

import android.content.Context

class MyPresenter(var view: MyContacts.MyView,var context: Context) :MyContacts.MyPresenter(view){

    override fun login(username: String, password: String) {

    }

    override fun cancel(tag: Any) {

    }

    override fun cancelAll() {

    }

}