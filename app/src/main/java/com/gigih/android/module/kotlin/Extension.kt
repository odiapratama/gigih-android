package com.gigih.android.module.kotlin

fun MutableList<String>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

fun runSwap() {
    val list = Loops.cars.toMutableList()
    list.swap(0, list.size - 1)
    println(list)
}