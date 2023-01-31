package com.hamcoding.screendetox.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object TimeConverter {

    fun getMidnightToday(): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
        }
    }

    fun getMillisBreakdown(data: Long, type: ConvertType): String {
        var millis = data
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        millis -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        millis -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis)
        if (type == ConvertType.NUM) {
            return StringBuilder().apply {
                append(timeToString(hours))
                append(":")
                append(timeToString(minutes))
                append(":")
                append(timeToString(seconds))
            }.toString()
        } else {
            return StringBuilder().apply {
                append(hours)
                append("시간 ")
                append(timeToString(minutes))
                append("분")
            }.toString()
        }
    }

    fun timeToString(data: Long): String {
        return if (data < 10) "0$data" else "$data"
    }

}