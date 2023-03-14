package com.hamcoding.screendetox

import android.app.Application
import android.util.Log
import com.hamcoding.screendetox.data.firebase.repository.RankRepository
import com.hamcoding.screendetox.data.firebase.repository.UpdateRepository
import com.hamcoding.screendetox.data.model.UsageProcessor
import com.hamcoding.screendetox.data.model.UsageStorage
import com.hamcoding.screendetox.data.firebase.repository.UserRepository
import com.hamcoding.screendetox.worker.WorkerManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ScreenApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        UsageStorage.getUsageMap(this)
        usageProcessor = UsageProcessor(this)
        updateMyUsageDuration()
        WorkerManager().startWork(this)
    }

    private fun updateMyUsageDuration() {
        if (UserRepository.getUserUid() != null) {
            Log.d("통신", usageProcessor.totalTime.toString())
            UpdateRepository.updateMyUsageDuration(usageProcessor.totalTime)
        }
    }

    companion object {
        lateinit var usageProcessor: UsageProcessor
    }
}