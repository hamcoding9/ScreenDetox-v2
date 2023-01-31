package com.hamcoding.screendetox.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.PackageManagerCompat
import com.hamcoding.screendetox.R
import com.hamcoding.screendetox.data.UsageStorage.usageMap
import com.hamcoding.screendetox.util.ConvertType
import com.hamcoding.screendetox.util.TimeConverter

class UsageProcessor(private val context: Context) {

    val totalTime: Long = usageMap.values.sum()

    fun processUsage(usageMap: Map<String, Long>): List<App> {
        val appList = mutableListOf<App>()
        for (event in usageMap) {
            val appName: String = try {
                val ai = context.packageManager.getApplicationInfo(
                    event.key,
                    PackageManager.GET_META_DATA
                )
                context.packageManager.getApplicationLabel(ai).toString()
            } catch (e: PackageManager.NameNotFoundException) {
                event.key
            }
            val appPercentage = event.value.toDouble() / totalTime * 100
            val appUsageDuration = TimeConverter.getMillisBreakdown(event.value, ConvertType.NUM)
            val appIcon = try {
                context.packageManager.getApplicationIcon(event.key)
            } catch (e: PackageManager.NameNotFoundException) {
                AppCompatResources.getDrawable(context, R.drawable.image_no_image_avaliable)
            }
            appList.add(App(appIcon!!, appName, appPercentage.toInt(), appUsageDuration))
        }
        return appList.sortedByDescending { it.usageDuration }
    }
}