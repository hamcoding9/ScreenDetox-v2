package com.hamcoding.screendetox.data.firebase.repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hamcoding.screendetox.util.ConvertType
import com.hamcoding.screendetox.util.TimeConverter

object UpdateRepository {
    private val database = Firebase.database.reference

    fun updateMyUsageDuration(usageDuration: Long) {
        val usageDurationKor = TimeConverter.getMillisBreakdown(usageDuration, ConvertType.KR)
        val userUpdate = hashMapOf<String, Any>(
            "usageDuration" to usageDurationKor
        )
        database.child("users").child(UserRepository.getUserUid()!!).updateChildren(userUpdate)
    }
}