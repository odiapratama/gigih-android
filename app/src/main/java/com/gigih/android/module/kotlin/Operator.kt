package com.gigih.android.module.kotlin

object Operator {

    init {
        // Mathematical Operator
        val area = Variable.height * Variable.width
        val circleArea = Variable.phi * 4 * 4

        // Increment & Decrement Operator
        var count = 1
        count++
        println(count)
        count--
        println(count)

        // Comparison Operator
        val isGreater = DataType.integer > DataType.short
        val isLower = DataType.double < DataType.float
        val isSameOrGreater = DataType.long >= DataType.float
        val isSameOrLower = DataType.long <= DataType.float

        // Equality Operator
        val checked = DataType.integer == 10
    }
}