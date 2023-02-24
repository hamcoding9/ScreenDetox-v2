package com.hamcoding.screendetox.data.model

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.hamcoding.screendetox.util.TimeConverter

object UsageStorage {

    private var _usageMap = mutableMapOf<String, Long>()
    val usageMap: Map<String, Long> = _usageMap

    fun getUsageMap(context: Context) {
        val beginTime = TimeConverter.getMidnightToday().timeInMillis
        val endTime = System.currentTimeMillis()

        val eventList = mutableListOf<UsageEvents.Event>()
        val usageStatsManager =
            context.getSystemService(AppCompatActivity.USAGE_STATS_SERVICE) as UsageStatsManager

        val usageEvents = usageStatsManager.queryEvents(beginTime, endTime)
        while (usageEvents.hasNextEvent()) {
            val currentEvent = UsageEvents.Event()
            usageEvents.getNextEvent(currentEvent)
            if (currentEvent.eventType == UsageEvents.Event.ACTIVITY_RESUMED
                || currentEvent.eventType == UsageEvents.Event.ACTIVITY_PAUSED
            ) {
                eventList.add(currentEvent)
                if (_usageMap[currentEvent.packageName] == null) {
                    _usageMap[currentEvent.packageName] = 0
                }
            }
        }

        for (i in 0 until eventList.size - 1) {
            val e0 = eventList[i]
            val e1 = eventList[i + 1]
            if (e0.eventType == UsageEvents.Event.ACTIVITY_RESUMED && e1.eventType == UsageEvents.Event.ACTIVITY_PAUSED && e0.className == e1.className) {
                val diff = (e1.timeStamp - e0.timeStamp)
                val prev = _usageMap[e0.packageName] ?: 0
                _usageMap[e0.packageName] = prev + diff
            }
        }

        if (eventList.size > 1) {
            val lastEvent = eventList[eventList.size - 1]
            if (lastEvent.eventType == UsageEvents.Event.ACTIVITY_RESUMED) {
                val diff = System.currentTimeMillis() - lastEvent.timeStamp
                val prev = _usageMap[lastEvent.packageName] ?: 0
                _usageMap[lastEvent.packageName] = prev + diff
            }
        }
    }
}