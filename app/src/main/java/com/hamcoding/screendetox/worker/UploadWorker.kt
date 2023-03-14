package com.hamcoding.screendetox.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hamcoding.screendetox.ScreenApplication
import com.hamcoding.screendetox.data.firebase.repository.RankRepository
import com.hamcoding.screendetox.data.firebase.repository.UserRepository

class UploadWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {

    override fun doWork(): Result {

        if (UserRepository.getUserUid() != null) {
            Log.d("worker", "I'm Working!")
            RankRepository().updateMyUsageDuration(ScreenApplication.usageProcessor.totalTime)
        }
        return Result.success()
    }

}