package com.hamcoding.screendetox.data

import com.hamcoding.screendetox.data.UsageStorage.usageMap
import com.hamcoding.screendetox.util.TimeConverter

class UsageProcessor(usageMap: Map<String, Long>) {

    val totalTime: Long = usageMap.values.sum()

    fun processUsage(): List<App> {
        val appList = mutableListOf<App>()
        for (event in usageMap) {
            val appName = event.key
            val appPercentage = event.value / totalTime * 100
            val appUsageDuration = TimeConverter.getMillisBreakdown(event.value)
            appList.add(App(appName, appPercentage.toInt(), appUsageDuration))
        }
        return appList
    }

}