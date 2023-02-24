package com.hamcoding.screendetox.ui.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hamcoding.screendetox.data.db.entity.RequestInfo
import com.hamcoding.screendetox.data.db.entity.RequestStatus
import com.hamcoding.screendetox.data.db.repository.UserRepository
import com.hamcoding.screendetox.databinding.ActivityNotificationBinding
import com.hamcoding.screendetox.util.DateFormatText

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private val onSubmitClick: (RequestInfo) -> Unit = {
        submitRequest(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setLayout()
    }

    private fun setLayout() {
        val adapter = NotificationAdapter(onSubmitClick)
        binding.rvNoti.adapter = adapter

        val notificationQuery = Firebase.database.reference.child("requests")
            .orderByChild("receiverEmail")
            .equalTo(Firebase.auth.currentUser?.email!!)
        notificationQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val notificationList = mutableListOf<RequestInfo>()
                    for (dataSnapshot in snapshot.children) {
                        val requestInfo = dataSnapshot.getValue(RequestInfo::class.java)
                        if (requestInfo?.requestStatus == RequestStatus.PENDING) {
                            notificationList.add(requestInfo)
                        }
                    }
                    adapter.submitList(notificationList)
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    private fun submitRequest(requestedData: RequestInfo) {
        val notificationQuery = Firebase.database.reference.child("requests")
            .orderByChild("receiverEmail")
            .equalTo(Firebase.auth.currentUser?.email!!)
        notificationQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val requestInfo = dataSnapshot.getValue(RequestInfo::class.java)
                        if (requestInfo?.senderEmail == requestedData.senderEmail) {
                            val key = requestedData.id
                            val requestDB = Firebase.database.reference.child("requests").child(key)
                            requestDB.addValueEventListener(object : ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        val data = snapshot.getValue(RequestInfo::class.java)
                                        data?.acceptedDate = DateFormatText.getCurrentDate()
                                        data?.requestStatus = RequestStatus.FRIEND
                                        requestDB.setValue(data)
                                        Firebase.database.reference.child("users").child(data?.senderUid!!).child("friends").child(
                                            UserRepository.getUserUid()!!).setValue(UserRepository.getUserUid())
                                        Firebase.database.reference.child("users").child(
                                            UserRepository.getUserUid()!!).child("friends").child(data.senderUid).setValue(data.senderUid)
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {}
                            })
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}