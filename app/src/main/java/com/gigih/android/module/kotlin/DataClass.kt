package com.gigih.android.module.kotlin

data class User(val name: String, val id: Int)

fun runDataClass() {
    val user = User("Alex", 1)
    println(user)

    val secondUser = User("Alex", 1)
    val thirdUser = User("Max", 2)

    println("user == secondUser: ${user == secondUser}")
    println("user == thirdUser: ${user == thirdUser}")

    // hashCode() function
    println(user.hashCode())
    println(secondUser.hashCode())
    println(thirdUser.hashCode())

    // copy() function
    println(user.copy())
    println(user === user.copy())
    println(user.copy(name = "Max"))
    println(user.copy(id = 3))

    println("name = ${user.component1()}")
    println("id = ${user.component2()}")
}