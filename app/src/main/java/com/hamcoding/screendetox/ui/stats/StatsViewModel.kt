package com.hamcoding.screendetox.ui.stats

import androidx.lifecycle.ViewModel
import com.hamcoding.screendetox.data.StatsRepository

class StatsViewModel(private val statsRepository: StatsRepository) : ViewModel() {

    val appList = statsRepository.getAppList()

}