package com.gigih.android.module.kotlin

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun runCoroutine() = runBlocking<Unit> {
    val a = async {
        println("I'm computing part of the answer")
        6
    }
    val b = async {
        println("I'm computing another part of the answer")
        7
    }
    println("The answer is ${a.await() * b.await()}")
}