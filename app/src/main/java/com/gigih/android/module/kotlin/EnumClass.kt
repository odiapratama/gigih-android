package com.gigih.android.module.kotlin

enum class State {
    IDLE, RUNNING, FINISHED                           // 1
}

fun runEnumClass() {
    val state = State.RUNNING                         // 2
    val message = when (state) {                      // 3
        State.IDLE -> "It's idle"
        State.RUNNING -> "It's running"
        State.FINISHED -> "It's finished"
    }
    println(message)
}