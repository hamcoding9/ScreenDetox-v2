package com.hamcoding.screendetox.data

import com.hamcoding.screendetox.util.ConvertType
import com.hamcoding.screendetox.util.TimeConverter.getMillisBreakdown

class AppRepository {

    private val usageProcessor = UsageProcessor(UsageStorage.usageMap)

    fun getAppList(): List<App> {
        return usageProcessor.processUsage()
    }

    fun getTotalTime(): String {
        return getMillisBreakdown(usageProcessor.totalTime, ConvertType.KR)
    }

}