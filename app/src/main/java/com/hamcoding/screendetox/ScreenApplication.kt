package com.hamcoding.screendetox

import android.app.Application
import com.hamcoding.screendetox.data.UsageStorage

class ScreenApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        UsageStorage.getUsageMap(this)
    }

}