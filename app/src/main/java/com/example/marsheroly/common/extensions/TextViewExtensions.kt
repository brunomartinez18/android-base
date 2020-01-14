package com.example.marsheroly.common.extensions

import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.widget.TextView

private val INVALID_DRAWABLE = ShapeDrawable()

fun TextView.updateCompoundDrawablesRelativeWithIntrinsicBounds(
    start: Drawable? = INVALID_DRAWABLE,
    top: Drawable? = INVALID_DRAWABLE,
    end: Drawable? = INVALID_DRAWABLE,
    bottom: Drawable? = INVALID_DRAWABLE
) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(
            if (start != INVALID_DRAWABLE) start else compoundDrawablesRelative[0],
            if (top != INVALID_DRAWABLE) top else compoundDrawablesRelative[1],
            if (end != INVALID_DRAWABLE) end else compoundDrawablesRelative[2],
            if (bottom != INVALID_DRAWABLE) bottom else compoundDrawablesRelative[3]
    )
}