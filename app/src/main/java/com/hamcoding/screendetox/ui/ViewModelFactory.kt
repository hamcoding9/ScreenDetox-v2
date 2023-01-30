package com.hamcoding.screendetox.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hamcoding.screendetox.data.AppRepository
import com.hamcoding.screendetox.ui.stats.StatsViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            val appRepository = AppRepository()
            return StatsViewModel(appRepository) as T
        } else {
            TODO("구현")
        }
    }

}