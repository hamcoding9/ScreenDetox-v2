package com.hamcoding.screendetox.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hamcoding.screendetox.data.db.repository.StatsRepository
import com.hamcoding.screendetox.util.DateFormatText

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