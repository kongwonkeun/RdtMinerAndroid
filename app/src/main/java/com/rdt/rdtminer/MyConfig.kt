package com.rdt.rdtminer

import android.content.Context

class MyConfig {

    companion object {

        const val DEBUG = true
        const val HOME_PATH = "rdtone"
        const val DATE_TIME_PATTERN = "yyyy-MM-dd_HH-mm-ss"
        const val SERVER_URL = "http://35.243.90.90:5000"
        const val SP7 = "\n\n\n\n\n\n\n"
        const val SP = "\n"

        var sContext: Context? = null
        var sServer: String? = null
        var sUser: String? = null
        var sPassword: String? = null
        var sFragmentId = FragId.HOME.i

        fun getUserInfo() {
            if (sContext != null) {
                val prefs = sContext?.getSharedPreferences(SPrefKey.USER.s, Context.MODE_PRIVATE)
                if (prefs != null) {
                    sServer = prefs.getString(SPrefKey.SERVER.s,null)
                    sUser = prefs.getString(SPrefKey.USER.s,null)
                    sPassword = prefs.getString(SPrefKey.PASSWORD.s,null)
                }
            }
        }

        fun setUserInfo() {
            if (sContext != null) {
                val prefs = sContext?.getSharedPreferences(SPrefKey.USER.s, Context.MODE_PRIVATE)
                if (prefs != null) {
                    val editor = prefs.edit()
                    if (sServer != null) editor.putString(SPrefKey.SERVER.s, sServer)
                    if (sUser != null) editor.putString(SPrefKey.USER.s, sUser)
                    if (sPassword != null) editor.putString(SPrefKey.PASSWORD.s, sPassword)
                    editor.apply()
                }
            }
        }

    }

}

/* EOF */
