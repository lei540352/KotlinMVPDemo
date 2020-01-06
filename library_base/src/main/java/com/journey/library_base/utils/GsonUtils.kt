package com.journey.library_base.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import java.lang.reflect.Type

class GsonUtils {

    companion object {
        @JvmField
        val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

        @JvmField
        val localGson = createLocalGson()

        @JvmField
        val sRemoteGson = createRemoteGson()

        @JvmStatic
        private fun createLocalGsonBuilder(): GsonBuilder {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.setLenient()
            gsonBuilder.setDateFormat(DATE_FORMAT)
            return gsonBuilder
        }

        @JvmStatic
        private fun createLocalGson(): Gson {
            return createLocalGsonBuilder().create()
        }

        @JvmStatic
        private fun createRemoteGson(): Gson {
            return createLocalGsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
        }

        @JvmStatic
        @Throws(JsonSyntaxException::class)
        fun <T> fromLocalJson(json: String, clazz: Class<T>): T? {
            try {
                return localGson.fromJson(json, clazz)
            } catch (e: JsonSyntaxException) {
                return null
            }

        }

        @JvmStatic
        fun <T> fromLocalJson(json: String, typeOfT: Type): T {
            return localGson.fromJson(json, typeOfT)
        }

        @JvmStatic
        fun toJson(src: Any): String {
            return localGson.toJson(src)
        }

        @JvmStatic
        fun toRemoteJson(src: Any): String {
            return sRemoteGson.toJson(src)
        }
    }
}