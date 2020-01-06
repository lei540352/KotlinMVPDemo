package com.journey.kotlindemo.network

import android.content.Context
import com.journey.kotlindemo.R

/***
 * 错误信息汇总
 */
enum class ApiErrorType(val code:Int,private val messageId:Int) {

    INTERNAL_SERVER_ERROR(500, R.string.service_error),
    BAD_GATEWAY(502, R.string.service_error),
    NET_FOUND(404, R.string.not_found),
    CONTECT_TIMEOUT(408, R.string.timeout),
    NETWORK_NOT_CONNECT(499, R.string.nerwork_error),
    UNKNOWN_ERROR(700, R.string.unknown_error);

    private val DEFAOLT_CODE = 1 // 默认code
    fun getApiError(context: Context):ApiError{

        return  ApiError(DEFAOLT_CODE,context.getString(messageId))
    }
}