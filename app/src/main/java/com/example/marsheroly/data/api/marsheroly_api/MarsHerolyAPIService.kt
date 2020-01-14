package com.gery.mobile.data.api.gery_api

import com.example.marsheroly.data.entities.MarsProperty
import retrofit2.Response
import retrofit2.http.*

interface MarsHerolyAPIService {

    @GET("realestate")
    suspend fun getMarsImages(): Response<List<MarsProperty>>

}