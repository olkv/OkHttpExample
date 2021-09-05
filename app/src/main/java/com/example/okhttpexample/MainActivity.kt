package com.example.okhttpexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.okhttpexample.databinding.ActivityMainBinding
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val dataModel: DataModel by viewModels()
    lateinit var login: CLogIn


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        dataModel.resToLogin.observe(this, {
            Log.d("okhttp", "Результат выполнения функции LogIn $it")
        })

        binding.btnConnect.setOnClickListener {
            loginRequest()
        }

        binding.btnGetList.setOnClickListener {
            listRequest()
        }

        setContentView(binding.root)
    }


    private fun loginRequest() {
        login = CLogIn(loginName = "oleg", loginPassword = "VjzDbrekbxrf22")
        val resLogIn = login.loginRequest("http://192.168.0.198:8000")

        resLogIn.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("okhttp", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response?.body?.string()

                login.token = (JSONObject(json).get("access")).toString()

                Log.d("okhttp","JSON Token: ${login.token}")

            }

        })

    }

    private fun listRequest() {
        val url = "http://192.168.0.198:8000/clients/list"

        val okHttpClient = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type","application/json")
            .addHeader("Authorization", "Bearer ${login.token}")
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