package com.gigih.android.module.kotlin

object Conditional {

    fun cookSteak(timeInMinutes: Int): String {
        if (timeInMinutes > 15) {
            return "Steak is Well Done"
        } else if (timeInMinutes > 7) {
            return "Steak is Medium"
        } else {
            return "Steak is Medium"
        }
    }

    fun whenAssign(obj: Any): Any {
        val result = when(obj) {
            1 -> "one"
            "Hello" -> 1
            is Long -> false
            else -> 42
        }
        return result
    }
}