package com.gery.mobile.data.entities.cards.medication

import com.squareup.moshi.Json
import java.io.Serializable

data class FriendRequest(
    @field:Json(name = "id") val friendRequestId: Int,
    @field:Json(name = "requesterId") val requesterId: Int,
    @field:Json(name = "requesterName") val requesterName: String,
    @field:Json(name = "statusRequest") val status: Boolean
): Serializable {

}