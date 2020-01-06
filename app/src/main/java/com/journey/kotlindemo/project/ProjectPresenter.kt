package com.journey.kotlindemo.project

import android.content.Context

class ProjectPresenter(var projectView:ProjectContacts.ProjectView,context:Context):ProjectContacts.ProjectPresenter(projectView) {

    override fun login(username: String, password: String) {

    }

    override fun cancel(tag: Any) {
    }

    override fun cancelAll() {
    }

}