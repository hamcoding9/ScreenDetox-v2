package com.hamcoding.screendetox.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hamcoding.screendetox.ScreenApplication
import com.hamcoding.screendetox.data.StatsRepository
import com.hamcoding.screendetox.ui.stats.StatsViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            val statsRepository = StatsRepository(ScreenApplication.usageProcessor)
            return StatsViewModel(statsRepository) as T
        } else {
            TODO("구현")
        }
    }

}