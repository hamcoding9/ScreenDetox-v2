package com.hamcoding.screendetox.util

import android.content.res.Resources
import java.util.*

object SystemConfiguration {

    val currentLocale: Locale = Resources.getSystem().configuration.locales.get(0)

}