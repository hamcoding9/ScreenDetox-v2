package com.hamcoding.screendetox.util

import java.util.*
import java.util.concurrent.TimeUnit

object TimeConverter {

    fun getMidnightToday(): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
        }
    }

    fun getMillisBreakdown(data: Long): String {
        var millis = data
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        millis -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        millis -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
        return "$hours:$minutes:$seconds"
    }

}