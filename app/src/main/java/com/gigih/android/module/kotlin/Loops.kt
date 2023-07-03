package com.gigih.android.module.kotlin

object Loops {

    val cars = arrayOf("toyota", "suzuki", "benz")

    fun runLoopFor() {
        for (car in cars) {
            print("$car ")
        }

        for ((index, car) in cars.withIndex()) {
            println("Item at $index is $car\n")
        }

        for (i in 0..cars.size - 1) print(cars[i] + " ")

        for (i in cars.size - 1 downTo 0) print(cars[i] + " ")

        for (i in cars.indices step 2) print(cars[i] + " ")

        for (i in 'a'..'z') print("$i ")
    }

    fun runLoop() {
        var bicycles = 0
        while (bicycles < 50) {
            bicycles ++
        }
        println("$bicycles bicycles in the bicycle rank\n")

        do {
            bicycles--
        } while (bicycles > 50)
        println("$bicycles bicycles in the bicycle rank\n")

        repeat(2) {
            print("Hello!")
        }
    }
}