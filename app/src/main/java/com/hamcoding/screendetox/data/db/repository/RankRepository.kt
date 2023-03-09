package com.hamcoding.screendetox.data.db.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hamcoding.screendetox.data.db.entity.User
import com.hamcoding.screendetox.util.ConvertType
import com.hamcoding.screendetox.util.TimeConverter.getMillisBreakdown

class RankRepository {

    private val database = Firebase.database.reference
    private val friendUidList = mutableListOf<String>()

    private var _friendList = mutableListOf<User>()
    val friendList: List<User> get() = _friendList

    fun updateMyUsageDuration(usageDuration: Long) {
        val usageDurationKor = getMillisBreakdown(usageDuration, ConvertType.KR)
        val userUpdate = hashMapOf<String, Any>(
            "usageDuration" to usageDurationKor
        )
        database.child("users").child(UserRepository.getUserUid()!!).updateChildren(userUpdate)
    }

    fun getFriendList() {
        getEmailList()
        loadFriendList()
    }

    private fun loadFriendList() {
        database.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    _friendList.clear()
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        if (friendUidList.contains(userSnapshot.key)) {
                            _friendList.add(user!!)
                        }
                        if (userSnapshot.key == UserRepository.getUserUid()) {
                            val myInfo = user?.copy(email = "${UserRepository.getUserEmail()}(나)")
                            _friendList.add(myInfo!!)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
        _friendList.sortBy { it.usageDuration }
    }

    private fun getEmailList() {
        database.child("users").child(UserRepository.getUserUid()!!).child("friends")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (dataSnapshot in snapshot.children) {
                            friendUidList.add(dataSnapshot.key!!)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }
}