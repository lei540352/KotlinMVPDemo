package com.journey.kotlindemo.home

import android.content.Context

class HomePresenter(var homeView: HomeContacts.HomeView,var context: Context) :HomeContacts.HomePresenter(homeView){


    override fun login(username: String, password: String) {
        homeView.showLoading()
    }

    override fun cancel(tag: Any) {

    }

    override fun cancelAll() {

    }

}