package com.hamcoding.screendetox.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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