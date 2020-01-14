package com.gery.mobile.common.extensions

import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.roundToLong

fun Double.round(commaPlaces: Int): Double {
    return when {
        commaPlaces < 0 -> 0.toDouble()
        commaPlaces == 0 -> this.roundToInt().toDouble()
        else -> {
            val tens = 10.toDouble().pow(commaPlaces)
            ((this * tens).roundToLong() / tens)
        }
    }
}