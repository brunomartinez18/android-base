package com.gery.mobile.data.api.gery_api

import android.provider.ContactsContract
import com.example.marsheroly.data.api.marsheroly_api.RetrofitFactory
import com.example.marsheroly.data.entities.MarsProperty
import retrofit2.Response

object MarsHerolyAPIClient {

    private const val BASE_URL = "https://mars.udacity.com/"
    //private const val PREFIX_TOKEN = "Bearer "

    private var service = RetrofitFactory
        .provideRetrofit(BASE_URL)
        .create(MarsHerolyAPIService::class.java)

    suspend fun getMarsImages(): Response<List<MarsProperty>> = service.getMarsImages()

   // private fun authToken(accessToken: String) = "$PREFIX_TOKEN$accessToken"
}