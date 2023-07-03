package com.gigih.android.module.kotlin

interface Fish {
    fun swim()
}

class Salmon() : Fish {
    override fun swim() {
        println("wish wish!")
    }
}