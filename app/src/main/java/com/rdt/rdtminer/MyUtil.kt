package com.rdt.rdtminer

import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.rdt.rdtminer.MyConfig.Companion.DATE_TIME_PATTERN
import com.rdt.rdtminer.MyConfig.Companion.DEBUG
import com.rdt.rdtminer.MyConfig.Companion.sContext
import java.text.SimpleDateFormat
import java.util.*

class MyUtil {

    companion object {

        fun showToast(resId: Int) {
            val toast = Toast.makeText(sContext, resId, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }

        fun showToast(message: String) {
            val toast = Toast.makeText(sContext, message, Toast.LENGTH_LONG)
            val layout = toast.view as LinearLayout
            if (layout.childCount > 0) {
                val text = layout.getChildAt(0) as TextView
                text.gravity = Gravity.CENTER_VERTICAL + Gravity.CENTER_HORIZONTAL
            }
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }

        fun showLog(tag: String, message: String) {
            if (DEBUG) {
                Log.d(tag, message)
            }
        }

        fun getDateTime(date: Date): String {
            return SimpleDateFormat(DATE_TIME_PATTERN, Locale.getDefault()).format(date)
        }

    }

}

/* EOF */