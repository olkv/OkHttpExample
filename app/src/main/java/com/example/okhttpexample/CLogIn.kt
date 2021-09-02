package com.example.okhttpexample

data class CLogIn(val loginName: String, val loginPassword: String)

data class CLogInRequest(val login: CLogIn)
