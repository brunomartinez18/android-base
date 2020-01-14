package com.example.marsheroly.data.api.marsheroly_api

import com.example.marsheroly.data.api.custom_adapters.CustomDateAdapter
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


internal object RetrofitFactory {

    fun provideRetrofit(
        baseUrl: String,
        headers: Map<String, String>? = null
    ): Retrofit {
        val moshiBuilder = Moshi.Builder()
            .add(CustomDateAdapter())

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(provideOkHttpClient(headers))
            .addConverterFactory(MoshiConverterFactory.create(moshiBuilder.build()))
            .build()
    }

    private fun provideOkHttpClient(
        headers: Map<String, String>? = null
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        if (headers != null) {
            okHttpClient.addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()
                headers.forEach { headerPair ->
                    requestBuilder.header(headerPair.key, headerPair.value)
                }

                val request = requestBuilder.build()
                chain.proceed(request)
            }
        }
        return okHttpClient.build()
    }

}