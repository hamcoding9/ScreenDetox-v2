package com.hamcoding.screendetox.ui.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.hamcoding.screendetox.R
import com.hamcoding.screendetox.data.RequestInfo
import com.hamcoding.screendetox.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setLayout()
    }

    private fun setLayout() {
        val adapter = NotificationAdapter()
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
                        notificationList.add(requestInfo!!.senderEmail)
                        Log.d("친구", "$requestInfo")
                        Log.d("친구", "${notificationList[0]}")
                    }
                    adapter.submitList(notificationList)
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }
}