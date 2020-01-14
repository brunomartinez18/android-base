package com.example.marsheroly.common.extensions

import androidx.viewpager.widget.ViewPager

fun ViewPager.next(): Boolean {
    val adapter = adapter ?: return false
    if (currentItem == adapter.count - 1) return false
    currentItem += 1
    return true
}

fun ViewPager.previous(): Boolean {
    if (currentItem == 0) return false
    currentItem -= 1
    return true
}