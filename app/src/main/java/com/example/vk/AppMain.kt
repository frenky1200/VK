package com.example.vk

import android.app.Application

import com.vk.sdk.VKSdk

class AppMain : Application() {

    override fun onCreate() {

        super.onCreate()

        instance = this
        VKSdk.initialize(instance)
    }

    companion object {

        const val vkid = 6743805
        const val defkey = "vh3oJlnP3U1UaoZ434Rt"
        const val seckey = "9bcba4a49bcba4a49bcba4a4829bad425999bcb9bcba4a4c02a7aaa30ab7a57edb9719c"

        var instance: AppMain? = null
            private set
    }

}