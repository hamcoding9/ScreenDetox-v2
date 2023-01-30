package com.hamcoding.screendetox.util

import java.util.*

object TimeConverter {

    fun getMidnightToday(): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
        }
    }

}