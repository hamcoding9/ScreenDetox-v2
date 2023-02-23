package com.hamcoding.screendetox.data

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DatabaseRepository {
    private val _isEmailNotExist = MutableStateFlow(false)
    private val _isEmailExist = MutableStateFlow(false)

    val isEmailNotExist: StateFlow<Boolean> = _isEmailNotExist
    val isEmailExist: StateFlow<Boolean> = _isEmailExist

    fun requestFriend(email: String) {
        val userQuery = Firebase.database.reference.child("users")
            .orderByChild("email")
            .equalTo(email)
        userQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val requestRef = Firebase.database.reference.child("requests").push()
                    val requestInfo = RequestInfo(requestRef.key!!, Firebase.auth.currentUser!!.email!!, UserRepository.getUserUid()!!, email)
                    requestRef.setValue(requestInfo)
                    Log.d("통신", "친구 요청 완료")
                    _isEmailExist.value = true
                } else {
                    Log.d("통신", "친구 찾을 수 없음")
                    _isEmailNotExist.value = true
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}