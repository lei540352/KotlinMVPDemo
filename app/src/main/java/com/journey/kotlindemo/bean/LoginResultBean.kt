package com.journey.kotlindemo.bean

data class LoginResultBean(var id:Int,var pluid:String? ,var email:String?,var username:String?,var password:String?,
                           var sex:String?,var birthday:String?,var user:List<*>,var userInfo:UserResultBean)

///** 会员推荐用标示ID */
//public String pluid = "";
///** 用户登陆账号 */
//public String email = "";
///** 用户姓名 */
//public String username = "";
///** 用户密码 */
//public String password = "";
///** 用户性别 */
//public String sex = "";
///** 用户出生日期 */
//public String birthday = "";