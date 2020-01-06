package com.journey.kotlindemo.bean

data class UserResultBean(var priread:Int, var user_name:String?, var follow_num:String?, var followme_num:String?, var user_info:String?,
                          var auth_email:Int, var isadmin:Int)

///**用户昵称*/
//public String nickname;
///***/
//public String user_name;
///**关注人*/
//public String follow_num;
///**粉丝*/
//public String followme_num;
///**是否有未读信息*/
//public int priread;
///** 标签 */
//public String user_info;
//public int auth_email;// 0 未激活 1 激活
//
//public int isadmin;// 0 否 1 是 管理员