package com.example.bootkotlin.entity

data class ResultResponse<T>(var code: Int = 200, var message: String, var result: T? = null)