package com.hamcoding.screendetox.data.firebase.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hamcoding.screendetox.data.firebase.entity.User
import com.hamcoding.screendetox.data.firebase.source.ApiClient
import com.hamcoding.screendetox.util.ConvertType
import com.hamcoding.screendetox.util.TimeConverter.getMillisBreakdown
import javax.inject.Inject

class RankRepository @Inject constructor(
    private val apiClient: ApiClient
) {
    private val myUid = UserRepository.getUserUid()!!
    suspend fun loadRankingList(): List<User> {
        val users = apiClient.getUsers()
        val friendList = users.filter {
            it.key == myUid
        }.values.first().friends.keys.toMutableList()
        friendList.add(myUid)
        return users.filter {
            friendList.contains(it.key)
        }.values.toList().sortedBy {
            it.usageDuration
        }
    }
}