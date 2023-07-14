package com.gigih.android.data.model

import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val name: String = "User $id"
)

val listUser = ArrayList<User>().apply {
    for (i in 0..20) {
        add(User())
    }
}