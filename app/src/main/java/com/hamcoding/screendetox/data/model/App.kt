package com.hamcoding.screendetox.data.model

import android.graphics.drawable.Drawable

data class App(
    var appIcon: Drawable,
    var appName: String,
    var usagePercentage: Int,
    var usageDuration: String
)