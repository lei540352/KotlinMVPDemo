package com.journey.kotlindemo.system

import android.content.Context

class SystemPresenter(var systemView:SystemContacts.SystemView,context: Context) :SystemContacts.SystemPresenter(systemView){

    override fun getHome(uid: String, flag: String) {
    }

    override fun cancel(tag: Any) {
    }

    override fun cancelAll() {
    }


}