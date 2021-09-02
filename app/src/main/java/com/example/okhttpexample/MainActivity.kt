package com.example.okhttpexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.okhttpexample.databinding.ActivityMainBinding
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var tokenJSON: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        /*
        val loginRequest = CLogInRequest(
            login = CLogIn(loginName = "oleg", loginPassword = "123")
        )
         */


        binding.btnConnect.setOnClickListener {
            // Log.d("okhttp",loginRequest.login.loginName)
            loginRequest();
        }

        binding.btnGetList.setOnClickListener {
            listRequest()
        }

        setContentView(binding.root)
    }

    private fun loginRequest() {
        val url = "http://192.168.0.198:8000/api/token/"

        val formBody = FormBody.Builder()
            .add("username","oleg")
            .add("password", "VjzDbrekbxrf22")
            .build()


        val okHttpClient = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .addHeader("Content-Type","application/json")
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.d("okhttp", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response?.body?.string()

                tokenJSON = (JSONObject(json).get("access")).toString()

                Log.d("okhttp",tokenJSON)
            }
        })

    }

    private fun listRequest() {
        val url = "http://192.168.0.198:8000/clients/list"

        val okHttpClient = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type","application/json")
            .addHeader("Authorization", "Bearer $tokenJSON")
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.d("okhttp", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response?.body?.string()

                //tokenJSON = (JSONObject(json).get("access")).toString()

                Log.d("okhttp", json.toString())
            }
        })

    }


}