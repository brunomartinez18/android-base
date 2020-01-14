package com.example.marsheroly.common.extensions

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setSupportsChangeAnimations(supportsChangeAnimations: Boolean) {
    (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = supportsChangeAnimations
}