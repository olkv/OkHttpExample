package com.example.okhttpexample

import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

data class CLogIn(val loginName: String, val loginPassword: String) {

    var token: String = ""

    private val urlFun = "/api/token/"

    fun loginRequest(urlServer: String): Call {
        val url = urlServer+urlFun

        var resFun: String = ""

        val formBody = FormBody.Builder()
            .add("username",loginName)
            .add("password", loginPassword)
            .build()


        val okHttpClient = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .addHeader("Content-Type","application/json")
            .build()

        return okHttpClient.newCall(request)

    }


}

