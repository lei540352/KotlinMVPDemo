package com.journey.kotlindemo.bean

data class RegisterResultBean(var id:Int,var admin:Boolean?,var chapterTops:List<*>?,
                              var collectIds:List<*>?, var message:String?, var email:String?,
                              var icon:String?, var nickname:String?,var password:String?, var publicName:String?,
                              var token:String?, var type:Int,var username:String?)
/**
 *"admin": false,
"chapterTops": [],
"collectIds": [],
"email": "",
"icon": "",
"id": 39685,
"nickname": "lei.jiangtao",
"password": "",
"publicName": "lei.jiangtao",
"token": "",
"type": 0,
"username": "lei.jiangtao"
 */