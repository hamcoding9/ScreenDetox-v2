package com.hamcoding.screendetox

import android.app.Application
import com.hamcoding.screendetox.data.AppContainer
import com.hamcoding.screendetox.data.UsageProcessor
import com.hamcoding.screendetox.data.UsageStorage

class ScreenApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        UsageStorage.getUsageMap(this)
        usageProcessor = UsageProcessor(this)
    }

    companion object {
        lateinit var usageProcessor: UsageProcessor
    }

}