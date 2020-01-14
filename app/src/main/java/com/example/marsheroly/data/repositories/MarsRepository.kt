package com.example.marsheroly.data.repositories

import com.example.marsheroly.data.entities.MarsProperty
import com.gery.mobile.data.api.gery_api.MarsHerolyAPIClient
import retrofit2.Response

interface MarsRepository {
    suspend fun getMarsImages(): Response<List<MarsProperty>>
}

object MarsRepositoryImpl: MarsRepository {

    private val client = MarsHerolyAPIClient

    override suspend fun getMarsImages(): Response<List<MarsProperty>> {
        return client.getMarsImages()
    }
}