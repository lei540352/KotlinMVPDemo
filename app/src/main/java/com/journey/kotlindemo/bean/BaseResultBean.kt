package com.journey.kotlindemo.bean

/**
 * 数据结构封装 解析
 */

data class BaseResultBean<out T>(var errorCode:Int, val data:T, var errorMsg:String)
//private T data;
//private T tlist;
//
//private int ID;
//private String ret;
//private String tip;
