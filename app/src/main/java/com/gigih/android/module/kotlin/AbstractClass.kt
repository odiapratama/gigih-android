package com.gigih.android.module.kotlin

abstract class Cat {
    abstract fun sound()
}

class Persia: Cat() {
    override fun sound() {
        println("meow meow!")
    }
}

class Angora: Cat() {
    override fun sound() {
        println("maw maw!")
    }
}

fun runAbstractClass() {
    val persia = Persia()
    persia.sound()

    val angora = Angora()
    angora.sound()
}