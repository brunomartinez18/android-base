package com.gery.mobile.data.entities.cards.medication

import com.squareup.moshi.Json
import java.io.Serializable

data class NeighborhoodEvent(
    @field:Json(name = "id") val neighborhoodEventId: Int,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "imageUrl") val imageSrc: String
): Serializable {

}