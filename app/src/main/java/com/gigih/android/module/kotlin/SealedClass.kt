package com.gigih.android.module.kotlin

sealed class Mammal(val name: String)                                                   // 1

class Tiger(val catName: String) : Mammal(catName)                                      // 2
class Human(val humanName: String, val job: String) : Mammal(humanName)

fun greetMammal(mammal: Mammal): String {
    when (mammal) {                                                                     // 3
        is Human -> return "Hello ${mammal.name}; You're working as a ${mammal.job}"    // 4
        is Tiger -> return "Hello ${mammal.name}"                                       // 5
    }                                                                                   // 6
}

fun runSealedClass() {
    println(greetMammal(Tiger("Snowy")))
}