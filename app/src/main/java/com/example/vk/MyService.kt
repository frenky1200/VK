package com.example.vk

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    lateinit var server: String
    lateinit var key: String
    lateinit var ts: String
    private lateinit var token: String

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent!!.extras != null){
            token = intent.extras!!.getString("token")!!
            getLongPool()
        }
        return START_STICKY
    }

    private fun getLongPool() {
        val post = VKRequest("messages.getLongPollServer?")
        post.executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                if (response!=null) {
                    server = response.json.getJSONObject("response").getString("server")
                    key = response.json.getJSONObject("response").getString("key")
                    ts = response.json.getJSONObject("response").getString("ts")
                    GlobalScope.launch {
                        longPool(server, key, ts)
                    }
                }
            }

            override fun onError(error: VKError?) {

            }
        })
    }

    fun longPool(server:String, key:String, ts:String) {
        val response = try {
            URL("https://$server?act=a_check&key=$key&ts=$ts&wait=25&mode=2&version=3")
                .openStream()
                .bufferedReader()
                .use { it.readText() }
        } catch (e: IOException) {
            "Error with ${e.message}."
        }
        val a = JSONObject(response).getJSONArray("updates")
        val b = JSONObject(response).getString("ts")
        GlobalScope.launch { longPool(server, key, b) }
        if (JSONObject(response).get("updates")=="[]"){
        } else {
            for (i in 0 until a.length())
            if (a.getJSONArray(i).getInt(0) == 4) {
                sendMessage(
                    a.getJSONArray(i).getInt(3),
                    a.getJSONArray(i).getString(5),
                    token,
                    VKSdk.getApiVersion()
                )
            }
        }
    }

    private fun sendMessage(id: Int, msg:String, token: String, v:String) {
        val msg1 = "Это%20Автоответчик%20повторяка.%20Вы%20написали%20мне:%20"
        val post = VKRequest("messages.send?user_id=$id&message=${msg1+msg.replace(" ", "%20")}&access_token=$$token&v=$v")
        post.executeWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {

            }

            override fun onError(error: VKError?) {

            }
        })
    }
}
