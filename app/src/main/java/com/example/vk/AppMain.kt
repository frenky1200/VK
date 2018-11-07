package com.example.vk

import android.app.Application

import com.vk.sdk.VKSdk

class MyApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        VKSdk.initialize(applicationContext);
        instance = this

    }

    companion object {
        var instance: MyApplication? = null
            private set
    }

}