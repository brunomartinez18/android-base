package com.gery.mobile.data.api.gery_api

import android.provider.ContactsContract
import com.example.marsheroly.data.api.marsheroly_api.RetrofitFactory
import retrofit2.Response

object MarsHerolyAPIClient {

    private const val BASE_URL = "https://api.gery.inhouse.decemberlabs.com/"
    private const val PREFIX_TOKEN = "Bearer "

    private var service = RetrofitFactory
        .provideRetrofit(BASE_URL)
        .create(MarsHerolyAPIService::class.java)

    private fun authToken(accessToken: String) = "$PREFIX_TOKEN$accessToken"
}