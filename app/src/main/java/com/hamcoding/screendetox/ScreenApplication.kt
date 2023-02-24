package com.hamcoding.screendetox

import android.app.Application
import com.hamcoding.screendetox.data.db.repository.RankRepository
import com.hamcoding.screendetox.data.model.UsageProcessor
import com.hamcoding.screendetox.data.model.UsageStorage
import com.hamcoding.screendetox.data.db.repository.UserRepository

class ScreenApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        UsageStorage.getUsageMap(this)
        usageProcessor = UsageProcessor(this)
        updateMyUsageDuration()
    }

    private fun updateMyUsageDuration() {
        if (UserRepository.getUserUid() != null) {
            RankRepository().updateMyUsageDuration(usageProcessor.totalTime)
        }
    }

    companion object {
        lateinit var usageProcessor: UsageProcessor
    }

}