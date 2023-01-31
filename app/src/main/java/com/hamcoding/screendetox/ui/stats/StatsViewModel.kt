package com.hamcoding.screendetox.ui.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamcoding.screendetox.data.App
import com.hamcoding.screendetox.data.AppRepository

class StatsViewModel(private val appRepository: AppRepository) : ViewModel() {

    val appList = appRepository.getAppList()
    val totalUsage = appRepository.getTotalTime()

}