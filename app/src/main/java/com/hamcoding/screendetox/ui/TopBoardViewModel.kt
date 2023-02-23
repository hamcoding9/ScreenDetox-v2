package com.hamcoding.screendetox.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hamcoding.screendetox.ScreenApplication.Companion.usageProcessor
import com.hamcoding.screendetox.data.RankRepository
import com.hamcoding.screendetox.data.StatsRepository
import com.hamcoding.screendetox.ui.rank.RankViewModel
import com.hamcoding.screendetox.util.ConvertType
import com.hamcoding.screendetox.util.DateFormatText
import com.hamcoding.screendetox.util.TimeConverter

class TopBoardViewModel(private val repository: StatsRepository) : ViewModel() {

    val totalUsage = repository.getTotalTime()
    val todayDateText = DateFormatText.getCurrentDateShort()

    companion object {
        fun provideFactory(repository: StatsRepository) = viewModelFactory {
            initializer {
                TopBoardViewModel(repository)
            }
        }
    }
}