package com.journey.kotlindemo.api

import com.journey.kotlindemo.bean.BaseResultBean
import com.journey.kotlindemo.bean.RegisterResultBean
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DataApi {

    @POST("/user/login")
    @FormUrlEncoded
    fun loginUser(@Field("username") username:String,
                  @Field("password") password:String)
            : Observable<BaseResultBean<RegisterResultBean>>

    @POST("/user/register")
    @FormUrlEncoded
    fun registerUser(@Field("username") username:String,
                  @Field("password") password:String,
                     @Field("repassword") repassword:String)
            :Observable<BaseResultBean<RegisterResultBean>>

}