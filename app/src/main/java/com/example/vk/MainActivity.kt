package com.example.vk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.*
import com.vk.sdk.api.VKRequest.VKRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import android.os.StrictMode
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var token:VKAccessToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //SomeActivity().setContentView(this)
        val vkid = 6743805
        val defkey = "vh3oJlnP3U1UaoZ434Rt"
        val seckey = "9bcba4a49bcba4a49bcba4a4829bad425999bcb9bcba4a4c02a7aaa30ab7a57edb9719c"

        button.setOnClickListener { onClick() }
        VKSdk.login(this, "messages")
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    fun onClick(){
        sendMessage(133528092,
            editText.text.toString(),
            token,
            VKSdk.getApiVersion()
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                override fun onResult(res: VKAccessToken) {
                    token = VKAccessToken.currentToken()
                    getLongPool()
                }
                override fun onError(error: VKError) {}
            })
        ) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    fun getLongPool() {
        val post = VKRequest("messages.getLongPollServer?")
        post.executeWithListener(object : VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                if (response!=null) {
                    val server = (response.json.get("response") as JSONObject).get("server").toString()
                    val key = (response.json.get("response") as JSONObject).get("key").toString()
                    val ts = (response.json.get("response") as JSONObject).get("ts").toString()
                    GlobalScope.launch {
                        LongPool(server, key, ts)
                    }
                }
            }

            override fun onError(error: VKError?) {
                error
            }
        })
    }

    fun LongPool(server:String, key:String, ts:String) {
            val response = try {
                URL("https://$server?act=a_check&key=$key&ts=$ts&wait=25&mode=2&version=3")
                    .openStream()
                    .bufferedReader()
                    .use { it.readText() }
            } catch (e: IOException) {
                "Error with ${e.message}."
            }
            println(response)

    }

    fun sendMessage(id: Int, msg:String, token: VKAccessToken, v:String) {
        val post = VKRequest("messages.send?user_id=$id&message=$msg&access_token=$${token.accessToken}&v=$v")
        post.executeWithListener(object : VKRequestListener() {
            override fun onComplete(response: VKResponse?) {

            }

            override fun onError(error: VKError?) {

            }
        })
    }
}
