package com.gigih.android.module.kotlin

open class Dog {
    open fun bark() {
        println("wow wow!")
    }
}

class Husky: Dog() {
    override fun bark() {
        super.bark()
        println("wif wif!")
    }
}

class Shiba: Dog() {
    override fun bark() {
        println("wan wan!")
    }
}

fun runOpenClass() {
    val dog = Dog()
    dog.bark()

    val husky = Husky()
    husky.bark()

    val shiba = Shiba()
    shiba.bark()
}