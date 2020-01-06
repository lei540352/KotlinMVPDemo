package com.journey.kotlindemo.network

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.journey.kotlindemo.bean.BaseResultBean
import com.journey.kotlindemo.view.LoadingDialog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

abstract class ApiResponse<T>(private val context:Context):Observer<BaseResultBean<out T>> {

    private var isShowLoading = true//是否显示

    constructor(context: Context,isShowLoading:Boolean = false):this(context){
        this.isShowLoading = isShowLoading
    }


    //返回成功
    abstract fun success(data:T)
    //返回失败
    abstract fun failure(statusCode:Int,apiError: ApiError)

    //准备请求网略
    override fun onSubscribe(d: Disposable) {
        //加载进度条
        if(isShowLoading){
//            LoadingDialog.show(context)
        }
        Log.i("ApiResponse","onSubscribe")
    }

    override fun onNext(t: BaseResultBean<out T>) {
        Log.i("ApiResponse","onNext :$t")
        if(t.data != null){
            success(t.data)
        }else{
            failure(700,ApiErrorType.UNKNOWN_ERROR.getApiError(context))
        }
    }

    override fun onComplete() {
        //取消进度条
//        LoadingDialog.cancel()
        Log.i("ApiResponse","onComplete}")
    }

    override fun onError(e: Throwable) {
        Log.i("ApiResponse","onError :${e.message}")
        //取消加载 进度条
//        LoadingDialog.cancel()
        //网络的错误 失败
        if(e is HttpException){
            val apiError:ApiError = when(e.code()){
                ApiErrorType.INTERNAL_SERVER_ERROR.code ->
                    ApiErrorType.INTERNAL_SERVER_ERROR.getApiError(context)
                ApiErrorType.BAD_GATEWAY.code ->
                    ApiErrorType.BAD_GATEWAY.getApiError(context)
                ApiErrorType.NET_FOUND.code ->
                    ApiErrorType.NET_FOUND.getApiError(context)
                //else 类似java default
                else ->otherError(e)
            }

            failure(e.code(),apiError)
            return
        }
        //没错误码的
        val apiErrorType:ApiErrorType = when(e){
            is UnknownHostException -> ApiErrorType.NETWORK_NOT_CONNECT
            is ConnectException -> ApiErrorType.NETWORK_NOT_CONNECT
            is SocketTimeoutException -> ApiErrorType.CONTECT_TIMEOUT
            else -> ApiErrorType.UNKNOWN_ERROR
        }
        failure(apiErrorType.code,apiErrorType.getApiError(context))
    }

    //其他错误
    private fun otherError(e :HttpException) = Gson().fromJson(e.response()?.errorBody()?.charStream(),ApiError::class.java)
}