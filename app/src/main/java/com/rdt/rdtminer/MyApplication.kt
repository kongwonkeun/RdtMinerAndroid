package com.rdt.rdtminer

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        MyConfig.sContext = applicationContext
        MyConfig.getUserInfo()

        if (MyConfig.sServer == null) {
            MyConfig.sServer = MyConfig.SERVER_URL
        }
    }

}

/* EOF */