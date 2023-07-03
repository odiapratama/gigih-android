package com.gigih.android.module.kotlin

val scopeData = Loops.cars.toMutableList()

fun runScopeFunction() {
    scopeData.let {

    }

    scopeData.run {

    }

    with(scopeData) {

    }

    scopeData.apply {

    }

    scopeData.also {
        it.add("bmw")
    }.remove("suzuki")
}