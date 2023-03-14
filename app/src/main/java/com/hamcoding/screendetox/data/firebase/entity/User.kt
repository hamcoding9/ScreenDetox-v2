package com.hamcoding.screendetox.data.firebase.entity

data class User(
    val email: String = "",
    val usageDuration: String? = "",
    val friends: Map<String, String> = emptyMap()
)