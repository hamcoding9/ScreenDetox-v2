package com.hamcoding.screendetox.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hamcoding.screendetox.data.db.entity.User
import com.hamcoding.screendetox.data.db.repository.RankRepository
import com.hamcoding.screendetox.data.db.repository.StatsRepository
import com.hamcoding.screendetox.data.db.repository.UserRepository
import com.hamcoding.screendetox.data.model.App
import com.hamcoding.screendetox.util.DateFormatText

class UsageViewModel(
    private val rankRepository: RankRepository,
    private val statsRepository: StatsRepository
) : ViewModel() {

    private val _rankingList = MutableLiveData<List<User>>()
    private val _rankNumber = MutableLiveData<Int>(1)
    private val _appList = MutableLiveData<List<App>>()

    val rankingList: LiveData<List<User>> = _rankingList
    val rankNumber: LiveData<Int> = _rankNumber
    val appList: LiveData<List<App>> = _appList
    val totalUsage = statsRepository.getTotalTime()
    val todayDateText = DateFormatText.getCurrentDateShort()

    fun loadRankingList() {
        rankRepository.getFriendList()
        _rankingList.value = rankRepository.friendList
        val userItem = rankRepository.friendList.find {
            it.email == "${UserRepository.getUserEmail()}(나)"
        }
        _rankNumber.value = rankRepository.friendList.indexOf(userItem) + 1
    }

    fun loadAppList() {
        _appList.value = statsRepository.getAppList()
    }

    companion object {
        fun provideFactory(rankRepository: RankRepository, statsRepository: StatsRepository) =
            viewModelFactory {
                initializer {
                    UsageViewModel(rankRepository, statsRepository)
                }
            }
    }
}