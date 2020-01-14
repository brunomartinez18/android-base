package com.example.marsheroly.common.extensions

import retrofit2.Response

fun <T> Response<T>.obtain(): T? {
    return if (isSuccessful) body() else throw Exception(errorBody()?.string())
}