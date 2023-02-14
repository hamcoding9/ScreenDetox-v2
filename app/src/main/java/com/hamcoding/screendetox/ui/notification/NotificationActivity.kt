package com.hamcoding.screendetox.ui.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.hamcoding.screendetox.R
import com.hamcoding.screendetox.data.RequestInfo
import com.hamcoding.screendetox.data.RequestStatus
import com.hamcoding.screendetox.databinding.ActivityNotificationBinding
import com.hamcoding.screendetox.util.DateFormatText

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    private val onSubmitClick: (String) -> Unit = {
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
                    Log.d("친구", "snapShot exists")
                    val notificationList = mutableListOf<String>()
                    for (dataSnapshot in snapshot.children) {
                        val requestInfo = dataSnapshot.getValue(RequestInfo::class.java)
                        if (requestInfo?.requestStatus == RequestStatus.PENDING) {
                            notificationList.add(requestInfo.senderEmail)
                        }
                        Log.d("친구", "$requestInfo")
                    }
                    adapter.submitList(notificationList)
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }

    private fun submitRequest(senderEmail: String) {
        val notificationQuery = Firebase.database.reference.child("requests")
            .orderByChild("receiverEmail")
            .equalTo(Firebase.auth.currentUser?.email!!)
        notificationQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val requestInfo = dataSnapshot.getValue(RequestInfo::class.java)
                        if (requestInfo?.senderEmail == senderEmail) {
                            val key = requestInfo.id
                            val requestDB = Firebase.database.reference.child("requests").child(key)
                            requestDB.addValueEventListener(object : ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        val data = snapshot.getValue(RequestInfo::class.java)
                                        data?.acceptedDate = DateFormatText.getCurrentDate()
                                        data?.requestStatus = RequestStatus.FRIEND
                                        requestDB.setValue(data)
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