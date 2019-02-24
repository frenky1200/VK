package com.example.vk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.style


class MainActivity : AppCompatActivity() {

    lateinit var token:VKAccessToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        SomeActivity().setContentView(this)
        val editText = find<EditText>(R.id.ww)
        val button = find<Button>(R.id.qq)
        val buttonStop = find<Button>(R.id.stop)
        button.setBackgroundResource(R.drawable.btn_rounded_corner)
        buttonStop.setBackgroundResource(R.drawable.btn_rounded_corner)

        if (VKSdk.isLoggedIn()){
            token = VKAccessToken.currentToken()
        } else {
            VKSdk.login(this, "messages")
        }
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        buttonStop.setOnClickListener {
            startService<MyService>()
            stopService<MyService>()
        }
        button.setOnClickListener {
            pref.edit().putString("text", editText.text.toString()).apply()
            stopService<MyService>()
            startService<MyService>("token" to token.accessToken, "text" to editText.text.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {
                override fun onResult(res: VKAccessToken) {
                    token = VKAccessToken.currentToken()
                }
                override fun onError(error: VKError) {
                    toast(error.errorMessage)
                }
            })
        ) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
