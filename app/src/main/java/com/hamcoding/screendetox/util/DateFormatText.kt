package com.hamcoding.screendetox.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatText {

    private const val DATE_YEAR_MONTH_DAY_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

    fun getCurrentDate(): String {
        val date = Date()
        return SimpleDateFormat(DATE_YEAR_MONTH_DAY_TIME_PATTERN, SystemConfiguration.currentLocale).format(date)
    }

}