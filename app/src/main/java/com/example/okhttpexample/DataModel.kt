package com.example.okhttpexample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel: ViewModel() {
    val resToLogin: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}