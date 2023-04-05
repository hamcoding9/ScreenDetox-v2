package com.hamcoding.screendetox.worker

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkerManager @Inject constructor() {

    fun startWork(appContext: Context) {
        Log.d("worker", "worker start")

        val uploadWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<UploadWorker>(30, TimeUnit.MINUTES)
                .build()

        WorkManager
            .getInstance(appContext)
            .enqueue(uploadWorkRequest)
    }

}