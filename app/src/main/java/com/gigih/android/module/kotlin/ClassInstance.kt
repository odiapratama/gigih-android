package com.gigih.android.module.kotlin

object ClassInstance {

    class Customer(type: Int) {

        init {
            println("Init block $type")
        }

        constructor(type: Int, name: String) : this(type) {
            println("${name.uppercase()} is $type")
        }
    }

    fun runClass() {
        Customer(1)
        Customer(2, "Gigih")
    }
}