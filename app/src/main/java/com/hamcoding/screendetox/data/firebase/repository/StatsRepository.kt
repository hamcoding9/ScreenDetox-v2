package com.hamcoding.screendetox.data.firebase.repository

import com.hamcoding.screendetox.data.model.UsageProcessor
import com.hamcoding.screendetox.data.model.UsageStorage
import com.hamcoding.screendetox.data.model.App
import com.hamcoding.screendetox.util.ConvertType
import com.hamcoding.screendetox.util.TimeConverter.getMillisBreakdown
import javax.inject.Inject

class StatsRepository @Inject constructor(private val usageProcessor: UsageProcessor) {

    fun getAppList(): List<App> {
        return usageProcessor.processUsage(UsageStorage.usageMap)
    }

    fun getTotalTime(): String {
        return getMillisBreakdown(usageProcessor.totalTime, ConvertType.KR)
    }

}