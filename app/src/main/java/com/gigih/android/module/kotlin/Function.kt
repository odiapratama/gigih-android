package com.gigih.android.module.kotlin

typealias lambda = (Int, Int) -> Int

object Function {

    fun sum(a: Int, b: Int) = a + b

    fun printSum(a: Int, b: Int = 2) {
        println("Sum of $a and $b is ${sum(a, b)}")
        higherFunction { x, y ->
            x + y
        }
    }

    fun higherFunction(body: lambda) {
        val result = body(2, 4)
        println("The sum of two number is: $result")
    }
}