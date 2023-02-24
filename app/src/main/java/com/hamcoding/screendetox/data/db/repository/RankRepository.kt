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

    val friendList = mutableListOf<User>()

    fun updateMyUsageDuration(usageDuration: Long) {
        val usageDuration = getMillisBreakdown(usageDuration, ConvertType.KR)
        val userUpdate = hashMapOf<String, Any>(
            "usageDuration" to usageDuration
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
                    friendList.clear()
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        if (friendUidList.contains(userSnapshot.key)) {
                            friendList.add(user!!)
                        }
                        if (userSnapshot.key == UserRepository.getUserUid()) {
                            friendList.add(user!!)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
        friendList.sortBy { it.usageDuration }
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