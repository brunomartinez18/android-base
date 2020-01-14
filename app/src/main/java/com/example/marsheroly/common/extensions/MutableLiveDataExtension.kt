package com.example.marsheroly.common.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<List<T>>.forceRefresh() {
    val new = mutableListOf<T>()
    new.addAll(value.orEmpty())
    value = new
}

fun <T> MutableLiveData<List<T>>.add(index: Int? = null, item: T) {
    val current = value.orEmpty().toMutableList()
    index?.let {
        current.add(it, item)
    } ?: run {
        current.add(item)
    }
    value = current
}

fun <T> MutableLiveData<List<T>>.addAll(index: Int? = null, items: List<T>) {
    val current = value.orEmpty().toMutableList()
    index?.let {
        current.addAll(it, items)
    } ?: run {
        current.addAll(items)
    }
    value = current
}

fun <T> MutableLiveData<List<T>>.removeAt(index: Int) {
    val current = value.orEmpty().toMutableList()
    current.removeAt(index)
    value = current
}

fun <T> MutableLiveData<List<T>>.remove(element: T) {
    val current = value.orEmpty().toMutableList()
    current.remove(element)
    value = current
}