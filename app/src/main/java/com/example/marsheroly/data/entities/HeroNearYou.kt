package com.gery.mobile.data.entities.cards.medication

import com.squareup.moshi.Json
import java.io.Serializable

data class HeroNearYou(
    @field:Json(name = "id") val heroId: Int,
    @field:Json(name = "heroName") val heroName: String,
    @field:Json(name = "heroLastname") val heroLastname: String
): Serializable {

}