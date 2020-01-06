package com.journey.library_base.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import android.text.InputFilter
import android.text.Spanned
import android.util.SparseArray
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.journey.library_base.R
import com.journey.library_base.base.BaseApplication
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class Utils {

    companion object{

        @JvmStatic
        private val TAG = "Utils"

        @JvmStatic
        private val DEBUG = false

        /**
         * 身份证正则表达式
         */
        @JvmStatic
        val IDCARD =
            "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})" +
                    "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}" +
                    "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))"
        /**
         * 手机号码的正则表达式
         */
        @JvmStatic
        val MOBILE = "^1[34578]\\d{9}"
        /**
         * Email正则表达式=^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$
         */
        @JvmStatic
        val EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"

        @JvmStatic
        fun isEmpty(str: String?): Boolean {
            return str == null || str.length <= 0
        }

        @JvmStatic
        fun isEmpty(sparseArray: SparseArray<*>?): Boolean {
            return sparseArray == null || sparseArray.size() <= 0
        }

        @JvmStatic
        fun isEmpty(collection: Collection<Any>?): Boolean {
            return collection == null || collection.size <= 0
        }

        @JvmStatic
        fun isEmpty(list: Map<out Any, Any>?): Boolean {
            return list == null || list.size <= 0
        }

        @JvmStatic
        fun isEmpty(bytes: ByteArray?): Boolean {
            return bytes == null || bytes.size <= 0
        }

        @JvmStatic
        fun isEmpty(strArr: Array<String>?): Boolean {
            return strArr == null || strArr.size <= 0
        }

        @JvmStatic
        fun nullAs(obj: Int?, def: Int): Int {
            return obj ?: def
        }

        @JvmStatic
        fun nullAs(obj: Long?, def: Long): Long {
            return obj ?: def
        }

        @JvmStatic
        fun nullAs(obj: Boolean?, def: Boolean): Boolean {
            return obj ?: def
        }

        @JvmStatic
        fun nullAs(obj: String?, def: String): String {
            return obj ?: def
        }

        @JvmStatic
        fun emptyAs(obj: String, def: String): String {
            return if (isEmpty(obj)) def else obj
        }

        @JvmStatic
        fun nullAsNil(obj: Int?): Int {
            return obj ?: 0
        }

        @JvmStatic
        fun nullAsNil(obj: Long?): Long {
            return obj ?: 0L
        }

        @JvmStatic
        fun nullAsNil(obj: String?): String {
            return obj ?: ""
        }


        @JvmStatic
        fun isEmpty(si: IntArray?): Boolean {
            return si == null || si.size == 0
        }

        /**
         * 判断值是否为 “null”
         */
        @JvmStatic
        fun getIsNull(context: String): String {
            return if (context == "null") "" else context
        }

        //判断是否存在字符串
        @JvmStatic
        fun containsAny(str: String, searchChars: String): Boolean {
            return if (str.length != str.replace(searchChars, "").length) {
                true
            } else false
        }

        @JvmStatic
        fun getTime(timeString: String): Long {
            var l: Long = 0
            val sdf = SimpleDateFormat("yyyyMMdd")
            val d: Date?
            try {
                d = sdf.parse(timeString)
                l = d!!.time
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return l
        }

        /**
         * @return
         */
        @JvmStatic
        fun timeslashData(time: Long): String {
            val sdr = SimpleDateFormat("yyyy-MM-dd")
            return sdr.format(Date(time * 1000L))

        }

        /**
         * @return
         */
        @JvmStatic
        fun timeslashData(time: Long, formatStr: String): String {
            val sdr = SimpleDateFormat(formatStr)
            return sdr.format(Date(time * 1000L))

        }

        /**
         * 版本号
         *
         * @param context
         * @return
         */
        @JvmStatic
        fun getVersion(context: Context): Int {
            val pm = context.packageManager
            val pi: PackageInfo
            var version = 0
            try {
                pi = pm.getPackageInfo(context.packageName, 0)
                version = pi.versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return version
        }

        /**
         * 版本名称
         *
         * @param context
         * @return
         */
        @JvmStatic
        fun getVersionName(context: Context): String? {
            val pm = context.packageManager
            val pi: PackageInfo
            var versionName: String? = null
            try {
                pi = pm.getPackageInfo(context.packageName, 0)
                versionName = pi.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return versionName
        }

        /**
         * 判断字段是否为身份证 符合返回ture
         *
         * @param str
         * @return boolean
         */
        @JvmStatic
        fun isIdCard(str: String): Boolean {
            if (StrisNull(str)) return false
            return if (str.trim { it <= ' ' }.length == 15 || str.trim { it <= ' ' }.length == 18) {
                Regular(str, IDCARD)
            } else {
                false
            }
        }

        /**
         * 判断是否为手机号码 符合返回ture
         *
         * @param str
         * @return boolean
         */
        @JvmStatic
        fun isMobile(str: String): Boolean {
            return Regular(str, MOBILE)
        }

        /**
         * 判断字段是否为Email 符合返回ture
         *
         * @param str
         * @return boolean
         */
        @JvmStatic
        fun isEmail(str: String): Boolean {
            return Regular(str, EMAIL)
        }

        /**
         * 匹配是否符合正则表达式pattern 匹配返回true
         *
         * @param str     匹配的字符串
         * @param pattern 匹配模式
         * @return boolean
         */
        @JvmStatic
        fun Regular(str: String?, pattern: String): Boolean {
            if (null == str || str.trim { it <= ' ' }.length <= 0)
                return false
            val p = Pattern.compile(pattern)
            val m = p.matcher(str)
            return m.matches()
        }

        @JvmStatic
        fun emailStr(email: String): String {
            val e = email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
            return if (e == "163.com") {
                "mail.163.com"
            } else if (e == "vip.163.com") {
                "vip.163.com"
            } else if (e == "126.com") {
                "mail.126.com"
            } else if (e == "qq.com" || e == "vip.qq.com" || e == "foxmail.com") {
                "mail.qq.com"
            } else if (e == "gmail.com") {
                "mail.google.com"
            } else if (e == "sohu.com") {
                "mail.sohu.com"
            } else if (e == "tom.com") {
                "mail.tom.com"
            } else if (e == "vip.sina.com") {
                "vip.sina.com"
            } else if (e == "sina.com.cn" || e == "sina.com") {
                "mail.sina.com.cn"
            } else if (e == "tom.com") {
                "mail.tom.com"
            } else if (e == "yahoo.com.cn" || e == "yahoo.cn") {
                "mail.cn.yahoo.com"
            } else if (e == "tom.com") {
                "mail.tom.com"
            } else if (e == "yeah.net") {
                "www.yeah.net"
            } else if (e == "21cn.com") {
                "mail.21cn.com"
            } else if (e == "hotmail.com") {
                "www.hotmail.com"
            } else if (e == "sogou.com") {
                "mail.sogou.com"
            } else if (e == "188.com") {
                "www.188.com"
            } else if (e == "139.com") {
                "mail.10086.cn"
            } else if (e == "189.cn") {
                "webmail15.189.cn/webmail"
            } else if (e == "wo.com.cn") {
                "mail.wo.com.cn/smsmail"
            } else if (e == "139.com") {
                "mail.10086.cn"
            } else {
                "mail.$e"
            }
        }


        /**
         * 判断字段是否为空 符合返回ture
         *
         * @param str
         * @return boolean
         */
        @Synchronized
        @JvmStatic
        fun StrisNull(str: String?): Boolean {
            return if (null == str || str.trim { it <= ' ' }.length <= 0) true else false
        }

        /**
         * @param datestr 日期字符串
         * @param day     相对天数，为正数表示之后，为负数表示之前
         * @return 指定日期字符串n天之前或者之后的日期
         */
        @JvmStatic
        fun getBeforeAfterDate(datestr: String, day: Int): java.sql.Date {
            val df = SimpleDateFormat("yyyyMMdd")
            var olddate: java.sql.Date? = null
            try {
                df.isLenient = false
                olddate = java.sql.Date(df.parse(datestr)!!.time)
            } catch (e: ParseException) {
                throw RuntimeException("日期转换错误")
            }

            val cal = GregorianCalendar()
            cal.time = olddate

            val Year = cal.get(Calendar.YEAR)
            val Month = cal.get(Calendar.MONTH)
            val Day = cal.get(Calendar.DAY_OF_MONTH)

            val NewDay = Day + day

            cal.set(Calendar.YEAR, Year)
            cal.set(Calendar.MONTH, Month)
            cal.set(Calendar.DAY_OF_MONTH, NewDay)

            return java.sql.Date(cal.timeInMillis)
        }

        @JvmStatic
        fun getDate(day: Int): String {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            var month = cal.get(Calendar.MONTH)
            if (month < 11) {
                month = month + day
            }
            val date = cal.get(Calendar.DATE)
            var m = (month + 1).toString() + ""
            if (month + 1 < 10) {
                m = "0$m"
            }
            var d = date.toString() + ""
            if (date < 10) {
                d = "0$d"
            }
            return y.toString() + m + d
        }

        /**
         * 禁止EditText输入空格
         * 禁止EditText输入特殊字符
         *
         * @param editText
         */
        @JvmStatic
        fun setEditTextInhibitInputSpeChat(editText: EditText) {
            val filter = InputFilter { source, start, end, dest, dstart, dend ->
                //                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                val speChat = "/^[a-zA-Z\\s]+$/"
                val pattern = Pattern.compile(speChat)
                val matcher = pattern.matcher(source.toString())
                if (matcher.find() || source == " ") {
                    ""
                } else
                    null
            }
            editText.filters = arrayOf(filter)
        }

        /**
         * 隐藏软键盘
         */
        @JvmStatic
        fun InputMethodManager(editText: EditText, mContext: Context) {
            (mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                editText.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        /**
         * 弹起软键盘
         */
        @JvmStatic
        fun loadWindow(mContext: Context) {
            val timer = Timer()
            timer.schedule(object : TimerTask() {

                override fun run() {
                    val imm = mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
                }
            }, 100)//
        }
    }
}