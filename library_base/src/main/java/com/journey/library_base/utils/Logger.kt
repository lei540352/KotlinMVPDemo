package com.journey.library_base.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import com.journey.library_base.perference.Perference
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class Logger {

    companion object {

        @JvmStatic
        var sEnabled = false

        @JvmField
        val sDefaultTag = "Main"

        @JvmStatic
        var sContext: Context? = null

        @JvmField
        val DB_CACHE_DATA_LOG = "db_cache.txt"

        @JvmField
        val  FIRST_LOG_FILE_NAME_MAIN = "log1.txt"

        @JvmField
        val  SECOND_LOG_FILE_NAME_MAIN = "log2.txt"

        @JvmField
        val  SP_KEY_CUR_LOG_FILE_FLAG_MAIN = "cur_log_file_flag"

        @JvmField
        val  FIRST_LOG_FILE_NAME_CHILD = "log1_child.txt"

        @JvmField
        val  SECOND_LOG_FILE_NAME_CHILD = "log2_child.txt"

        @JvmField
        val  SP_KEY_CUR_LOG_FILE_FLAG_CHILD = "cur_log_file_flag_child"

        @JvmStatic
        var isMainProcess = true

        @JvmStatic
        var mLogRandomAccessFile: RandomAccessFile? = null

        @JvmField
        val  MAX_FILE_LENGTH = (1 * 1024 * 1024).toLong() //单个文件限制大小

        @JvmField
        val  CHECK_SIZE_TIMES = 10 //每写入10次，检查一下文件大小是否超出限制

        @JvmStatic
        var FIRST_FILE_NAME = FIRST_LOG_FILE_NAME_MAIN //第一个日志文件的名称

        @JvmStatic
        var SECOND_FILE_NAME = SECOND_LOG_FILE_NAME_MAIN //第二个日志文件的名称

        @JvmStatic
        var SP_KEY_CUR_LOG_FILE_FLAG = SP_KEY_CUR_LOG_FILE_FLAG_MAIN //存储当前操作的文件标识的key

        @JvmStatic
        var curLogFileFlag = 0 //当前操作的文件标志位，0-log1.txt  1-log2.txt;

        @JvmStatic
        var curWriteTime: Long = 0 //当前已写入次数

        @JvmField
        val sDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())

        @JvmStatic
        var timeStr: String
            get() = sDateFormat.format(Date(System.currentTimeMillis()))
            set(value) {}

        @JvmStatic
        val isMainThread: Boolean
            get() = Looper.getMainLooper() == Looper.myLooper()

        /**
         * 读取最后一段logger   最多300行
         */
        //是否需要读取上一个文件
        @JvmStatic
        val lastLogger: ArrayList<String>
            get() {
                val MAX_SIZE = (50 * 1024).toLong()
                val data = ArrayList<String>()
                val rootFile = getDirFile(sContext)
                var randomAccessFile: RandomAccessFile? = null
                var lastRandomAccessFile: RandomAccessFile? = null
                try {
                    var curFileFlag:Int by Perference(SP_KEY_CUR_LOG_FILE_FLAG, 0)
                    val fileName = if (curFileFlag == 0) FIRST_FILE_NAME else SECOND_FILE_NAME
                    val curLogFile = File(rootFile!!.absolutePath + File.separator + fileName)
                    if (!curLogFile.exists()) {
                        return data
                    }
                    randomAccessFile = RandomAccessFile(curLogFile, "rw")
                    if (randomAccessFile.length() < MAX_SIZE) {
                        val lastFileName = if (curFileFlag != 0) FIRST_FILE_NAME else SECOND_FILE_NAME
                        val lastLogFile = File(rootFile.absolutePath + File.separator + lastFileName)
                        if (lastLogFile.exists()) {
                            val lastFileReadSize = MAX_SIZE - randomAccessFile.length()
                            lastRandomAccessFile = RandomAccessFile(lastLogFile, "rw")
                            lastRandomAccessFile.seek(
                                Math.max(
                                    0,
                                    lastRandomAccessFile.length() - lastFileReadSize
                                )
                            )
                            while (lastRandomAccessFile.filePointer < lastRandomAccessFile.length()) {
                                data.add(lastRandomAccessFile.readLine())
                            }
                        }
                    }

                    randomAccessFile.seek(Math.max(0, randomAccessFile.length() - MAX_SIZE))
                    while (randomAccessFile.filePointer < randomAccessFile.length()) {
                        data.add(randomAccessFile.readLine())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    try {
                        randomAccessFile?.close()
                        if (randomAccessFile != null) {
                            lastRandomAccessFile!!.close()
                        }
                    } catch (e: Exception) {

                    }

                }
                return data
            }

        @JvmField
        val  cacheList = LinkedBlockingQueue<String>()

        @JvmStatic
        fun v(msg: String) {
            if (sEnabled) {
                Log.v(sDefaultTag, "" + msg)
            }
        }

        @JvmStatic
        fun v(tag: String, msg: String) {
            if (sEnabled) {
                Log.v(tag, "" + msg)
            }
        }

        @JvmStatic
        fun d(msg: String) {
            if (sEnabled) {
                Log.d(sDefaultTag, "" + msg)
                writeDisk(sDefaultTag, "" + msg, "D")
            }
        }

        @JvmStatic
        fun d(tag: String, msg: String) {
            if (sEnabled) {
                Log.d(tag, "" + msg)
                writeDisk(tag, "" + msg, "D")
            }
        }

        @JvmStatic
        fun e(tag: String, t: Throwable) {
            if (sEnabled) {
                Log.e(tag, "exception", t)
            }
            writeDisk(tag, "exception:" + Log.getStackTraceString(t), "E")
        }

        @JvmStatic
        fun e(tag: String, msg: String, t: Throwable) {
            if (sEnabled) {
                Log.e(tag, "" + msg, t)
            }
            writeDisk(tag, msg + ":" + Log.getStackTraceString(t), "E")
        }

        @JvmStatic
        fun e(tag: String, msg: String) {
            if (sEnabled) {
                Log.e(tag, "" + msg)
            }
            writeDisk(tag, msg, "E")
        }

        @JvmStatic
        fun i(tag: String, msg: String) {
            if (sEnabled) {
                Log.i(tag, "" + msg)
            }
            writeDisk(tag, msg, "I")
        }

        @JvmStatic
        fun i(msg: String) {
            if (sEnabled) {
                Log.i(sDefaultTag, "" + msg)
            }
            writeDisk("Common", msg, "I")
        }

        @JvmStatic
        fun printInstance(obj: Any?, msg: String) {
            if (obj != null) {
                i(
                    String.format(
                        "   %s@%s: %s", obj.javaClass.simpleName,
                        Integer.toHexString(obj.hashCode()), msg
                    )
                )
            }
        }

        @JvmStatic
        fun w(tag: String, msg: String) {
            if (sEnabled) {
                Log.w(tag, "" + msg)
            }
            writeDisk(tag, msg, "W")
        }

        /**
         * 获取日志文件目录
         * 如果不存在，则创建目录
         */
        @JvmStatic
        fun getDirFile(context: Context?): File? {
            if (context == null) {
                return null
            }
            val basePath = context.filesDir.absolutePath
            val file = File(basePath + File.separator + "log")
            if (!file.exists()) {
                file.mkdirs()
            }
            return file
        }

        /**
         * 删除老的日志文件
         */
        @JvmStatic
        fun tryDeleteOldFile(context: Context) {
            val oldFile = context.getExternalFilesDir("log")
            if (oldFile != null && oldFile.exists()) {
                oldFile.deleteOnExit()
            }
        }

        @JvmStatic
        fun initV(context: Context?, isDebug: Boolean) {
            if (context != null) {
                sContext = context
                sEnabled = isDebug
                isMainProcess = TextUtils.equals(getProcessName(context), context.packageName)
                if (!isMainProcess) { //非主进程使用另外一套文件
                    FIRST_FILE_NAME = FIRST_LOG_FILE_NAME_CHILD
                    SECOND_FILE_NAME = SECOND_LOG_FILE_NAME_CHILD
                    SP_KEY_CUR_LOG_FILE_FLAG = SP_KEY_CUR_LOG_FILE_FLAG_CHILD
                }
                val loggerTaskThread = LoggerTaskThread()
                loggerTaskThread.name = "WebullLogger"
                loggerTaskThread.start()
            }
            d("appinit", "app start")
        }


        /**
         * 获取进程名
         *
         * @param context
         * @return
         */
        @JvmStatic
        fun getProcessName(context: Context): String? {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val runningApps = am.runningAppProcesses ?: return null
            for (proInfo in runningApps) {
                if (proInfo.pid == android.os.Process.myPid()) {
                    if (proInfo.processName != null) {
                        return proInfo.processName
                    }
                }
            }
            return null
        }

        @JvmStatic
        fun writeDisk(tag: String, message: String, level: String) {
            val ret = cacheList.offer(
                StringBuilder().append("[pid:").append(android.os.Process.myPid()).append("][")
                    .append(timeStr).append(": ")
                    .append(level).append("/")
                    .append(tag).append("]")
                    .append("【mainThread:").append(isMainThread).append("】")
                    .append(message).toString()
            )
        }

        @JvmStatic
        fun doWriteDisk(msg: String) {
            try {
                if (mLogRandomAccessFile != null) {
                    val msgByte = msg.toByteArray(charset("UTF-8"))
                    if (curWriteTime % CHECK_SIZE_TIMES == 0L) {
                        if (mLogRandomAccessFile!!.length() + msgByte.size > MAX_FILE_LENGTH) {
                            //切换文件
                            curLogFileFlag = if (curLogFileFlag == 0) 1 else 0
                            switchLogRandomAccessFile(if (curLogFileFlag == 0) FIRST_FILE_NAME else SECOND_FILE_NAME)
                            var curFileFlag:Int by Perference(SP_KEY_CUR_LOG_FILE_FLAG, 0)
                            curFileFlag = curLogFileFlag
                        }
                        curWriteTime = 0
                    }
                    mLogRandomAccessFile!!.write(msgByte)
                    mLogRandomAccessFile!!.writeBytes("\r\n")
                    curWriteTime++
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mLogRandomAccessFile = null
            }

        }

        @JvmStatic
        fun switchLogRandomAccessFile(fileName: String) {
            if (sContext == null) {
                return
            }
            val file = getDirFile(sContext)
            try {
                val newLogFile = File(file!!.absolutePath + File.separator + fileName)
                if (newLogFile.exists()) {
                    newLogFile.delete()
                }
                newLogFile.createNewFile()
                mLogRandomAccessFile = RandomAccessFile(newLogFile, "rw")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        /**
         * 批量压缩文件（夹）
         *
         * @param resFileList 要压缩的文件（夹）列表
         * @param zipFile     生成的压缩文件
         * @param comment     压缩文件的注释
         * @throws IOException 当压缩过程出错时抛出
         */
        @Throws(IOException::class)
        fun zipFiles(resFileList: Collection<File>, zipFile: File, comment: String) {
            val zipout = ZipOutputStream(
                BufferedOutputStream(
                    FileOutputStream(
                        zipFile
                    ), 1024
                )
            )
            for (resFile in resFileList) {
                zipFile(resFile, zipout, "")
            }
            zipout.setComment(comment)
            zipout.close()
        }

        /**
         * 压缩文件
         *
         * @param resFile  需要压缩的文件（夹）
         * @param zipout   压缩的目的文件
         * @param rootpath 压缩的文件路径
         * @throws FileNotFoundException 找不到文件时抛出
         * @throws IOException           当压缩过程出错时抛出
         */

        @JvmStatic
        @Throws(FileNotFoundException::class, IOException::class)
        fun zipFile(resFile: File, zipout: ZipOutputStream, rootpath: String) {
            var rootpath = rootpath
            rootpath = (rootpath + (if (rootpath.trim { it <= ' ' }.length == 0) "" else File.separator)
                    + resFile.name)
            rootpath = String(rootpath.toByteArray(charset("8859_1")), charset("GB2312"))
            if (resFile.isDirectory) {
                val fileList = resFile.listFiles()
                for (file in fileList!!) {
                    zipFile(file, zipout, rootpath)
                }
            } else {
                val buffer = ByteArray(1024)
                val inputS = BufferedInputStream(FileInputStream(resFile), 1024)
                zipout.putNextEntry(ZipEntry(rootpath))
                var realLength: Int = 0

                while ({realLength = inputS.read(buffer);realLength}() != -1){
                    zipout.write(buffer, 0, realLength)
                }
                inputS.close()
                zipout.flush()
                zipout.closeEntry()
            }
        }


        @JvmStatic
        fun getZipLoggerFileWithLogcat(logcatFilePath: String, isWebull: Boolean): String {
            try {
                val rootFile = getDirFile(sContext)
                if (rootFile != null && rootFile.exists() && rootFile.isDirectory) {
                    val zipFile = File(rootFile.absolutePath + File.separator + "log.zip")
                    if (zipFile.exists()) {
                        zipFile.delete()
                    }
                    val files = ArrayList<File>()

                    if (!TextUtils.isEmpty(logcatFilePath)) {
                        val logcatFile = File(logcatFilePath)
                        if (logcatFile.exists()) {
                            files.add(logcatFile)
                            d("add logcat file to zipfile list")
                        }
                    }

                    val firstLogFile =
                        File(rootFile.absolutePath + File.separator + FIRST_LOG_FILE_NAME_MAIN)
                    if (firstLogFile.exists()) {
                        files.add(firstLogFile)
                        d("add firstLogFile file to zipfile list")
                    }

                    val secondLogFile =
                        File(rootFile.absolutePath + File.separator + SECOND_LOG_FILE_NAME_MAIN)
                    if (secondLogFile.exists()) {
                        files.add(secondLogFile)
                        d("add secondLogFile file to zipfile list")
                    }


                    val otherFirstLogFile =
                        File(rootFile.absolutePath + File.separator + FIRST_LOG_FILE_NAME_CHILD)
                    if (otherFirstLogFile.exists()) {
                        files.add(otherFirstLogFile)
                        d("add firstLogFile file to zipfile list")
                    }

                    val otherSecondLogFile =
                        File(rootFile.absolutePath + File.separator + SECOND_LOG_FILE_NAME_CHILD)
                    if (otherSecondLogFile.exists()) {
                        files.add(otherSecondLogFile)
                        d("add secondLogFile file to zipfile list")
                    }

                    val dbFile = sContext!!.getDatabasePath("stocks")
                    if (dbFile != null && dbFile.exists()) {
                        files.add(dbFile)
                        d("add dbFile file to zipfile list")
                    }

                    ///data/data/org.dayup.stocks/shared_prefs/org.dayup.stocks_preferences.xml
                    var spFilePath = ""
                    if (isWebull) {
                        spFilePath =
                            sContext!!.filesDir.parent!! + "/shared_prefs/com.webull.trade_preferences.xml"
                    } else {
                        spFilePath =
                            sContext!!.filesDir.parent!! + "/shared_prefs/org.dayup.stocks_preferences.xml"
                    }

                    val spFile = File(spFilePath)
                    if (spFile != null && spFile.exists()) {
                        files.add(spFile)
                        d("add spFile file to zipfile list")
                    }

                    val file = File(rootFile.absolutePath + File.separator + DB_CACHE_DATA_LOG)
                    if (file.exists()) {
                        files.add(file)
                    }

                    zipFiles(files, zipFile, "")
                    return zipFile.absolutePath
                }
            } catch (e: Exception) {
                Log.d("zipFile error:", e.message)
            }

            return ""
        }

        class LoggerTaskThread : Thread() {
            override fun run() {
                try {
                    Logger.tryDeleteOldFile(sContext!!)
                    val rootDir = getDirFile(sContext)
                    try {
                        var curFileFlag:Int by Perference(SP_KEY_CUR_LOG_FILE_FLAG, 0)
                        curLogFileFlag = curFileFlag
                        val fileName = if (curLogFileFlag == 0) FIRST_FILE_NAME else SECOND_FILE_NAME
                        val newLogFile = File(rootDir!!.absolutePath + File.separator + fileName)
                        if (!newLogFile.exists()) {
                            newLogFile.createNewFile()
                        }
                        mLogRandomAccessFile = RandomAccessFile(newLogFile, "rw")
                        mLogRandomAccessFile!!.seek(mLogRandomAccessFile!!.length())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    while (!Thread.interrupted()) {
                        doWriteDisk(cacheList.take())
                    }
                } catch (e: Exception) {

                }
            }
        }
    }
}
