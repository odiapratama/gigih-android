package com.gigih.android.module.kotlin

object Variable {

    var height = 5 // type inference
    var width: Int = 6 // explicit type
    const val phi: Double = 3.14 // immutable

    init {
        height = 7 // reassigned variable
        width = 8 // reassigned variable
//        phi = 6.4 // error
    }
}