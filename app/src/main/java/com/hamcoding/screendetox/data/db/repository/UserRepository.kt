package com.hamcoding.screendetox.data.db.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hamcoding.screendetox.data.db.entity.User

object UserRepository {

    fun generateUserItem(): User {
        val email = getUserEmail()!!
        return User(email, "0시간")
    }

    fun getUserUid(): String? {
        return if (Firebase.auth.currentUser != null) {
            Firebase.auth.currentUser?.uid
        } else {
            null
        }
    }

    fun getUserEmail(): String? {
        return if (Firebase.auth.currentUser != null) {
            Firebase.auth.currentUser?.email
        } else {
            null
        }
    }

}