package com.journey.kotlindemo.network

import com.journey.kotlindemo.BuildConfig
import com.journey.kotlindemo.api.DataApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor(){//这里是单例

    lateinit var service: DataApi

    lateinit var retrofit:Retrofit

    //需要返回一个单例
    private object Holder{
        val INSTANCE = ApiClient()
    }

    companion object{
        val instance by lazy{
            Holder.INSTANCE
        }
    }

    fun createRetrofit(){
        val okHttpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(HttpLoggingInterceptor().setLevel(
                if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            ))
        }.build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        service = retrofit.create(DataApi::class.java)
    }

    //区分不同api的请求
    fun <T> getService(service:Class<T>):T = retrofit.create(service)
}