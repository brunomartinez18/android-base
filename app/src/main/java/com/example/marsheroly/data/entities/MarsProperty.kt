package com.example.marsheroly.data.entities

import com.squareup.moshi.Json
import java.io.Serializable

data class MarsProperty(
    @field:Json(name = "img_src") val imgSrcUrl: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "price") val price: Double
): Serializable {

}