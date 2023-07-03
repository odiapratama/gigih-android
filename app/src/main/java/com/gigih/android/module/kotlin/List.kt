package com.gigih.android.module.kotlin

object List {

    fun runList() {
        val list = Loops.cars.toList()
        println(Loops.cars)
    }

    fun runMutableList() {
        val mutableCars = Loops.cars.toMutableList()
        mutableCars.remove("toyota")
        println(mutableCars)
    }

    fun runArray() {
        val combined = Loops.cars + Loops.cars
        println(combined)
    }
}