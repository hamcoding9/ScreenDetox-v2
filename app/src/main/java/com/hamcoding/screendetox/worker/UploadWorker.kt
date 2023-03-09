package com.hamcoding.screendetox.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hamcoding.screendetox.ScreenApplication
import com.hamcoding.screendetox.data.db.repository.RankRepository
import com.hamcoding.screendetox.data.db.repository.UserRepository
import com.hamcoding.screendetox.data.model.UsageProcessor
import com.hamcoding.screendetox.data.model.UsageStorage

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