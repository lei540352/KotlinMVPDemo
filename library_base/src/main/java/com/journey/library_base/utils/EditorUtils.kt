package com.journey.library_base.utils

import android.annotation.TargetApi
import android.content.SharedPreferences
import android.os.Build

class EditorUtils {

    companion object{

        @JvmStatic
        fun fastCommit(editor: SharedPreferences.Editor) {
            // edit.apply could not commit your preferences changes in time on
            // Android 4.3
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                GingerbreadCompatLayer.fastCommit(editor)
            } else {
                // FIXME: there's no fast commit below GINGERBREAD.
                editor.commit()
            }
        }

        @TargetApi(9)
        private object GingerbreadCompatLayer {
            fun fastCommit(editor: SharedPreferences.Editor) {
                editor.apply()
            }
        }
    }
}