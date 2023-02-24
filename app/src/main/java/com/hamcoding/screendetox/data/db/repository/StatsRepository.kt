package com.hamcoding.screendetox.data.db.repository

import com.hamcoding.screendetox.data.model.UsageProcessor
import com.hamcoding.screendetox.data.model.UsageStorage
import com.hamcoding.screendetox.data.model.App
import com.hamcoding.screendetox.util.ConvertType
import com.hamcoding.screendetox.util.TimeConverter.getMillisBreakdown

class StatsRepository(private val usageProcessor: UsageProcessor) {

    fun getAppList(): List<App> {
        return usageProcessor.processUsage(UsageStorage.usageMap)
    }

    fun getTotalTime(): String {
        return getMillisBreakdown(usageProcessor.totalTime, ConvertType.KR)
    }

}