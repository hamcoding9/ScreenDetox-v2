package com.hamcoding.screendetox.data.firebase.entity

data class User(
    val email: String = "",
    val usageDuration: String? = "",
)

data class UserDto(
    val email: String = "",
    val usageDuration: String? = "",
    val friends: Map<String, String>
)