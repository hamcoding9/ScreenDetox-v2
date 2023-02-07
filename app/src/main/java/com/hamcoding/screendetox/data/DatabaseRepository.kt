package com.hamcoding.screendetox.data

import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DatabaseRepository {

    fun requestFriend(email: String): Boolean {
        val isExist = true
        val userQuery = Firebase.database.reference.child("users")
            .orderByChild("email")
            .equalTo(email)
        userQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val requestRef = Firebase.database.reference.child("requests").push()
                    val requestInfo = RequestInfo(Firebase.auth.currentUser!!.email!!, email)
                    requestRef.setValue(requestInfo)
                    Log.d("친구", "친구 요청 완료")
                }
                else {
                    Log.d("친구", "친구 찾을 수 없음")
                }
            }
            override fun onCancelled(error: DatabaseError) { }
        })
        return isExist
    }

}