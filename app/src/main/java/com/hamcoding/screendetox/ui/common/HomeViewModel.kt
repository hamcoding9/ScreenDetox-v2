package com.hamcoding.screendetox.ui.common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hamcoding.screendetox.data.firebase.source.ApiClient
import com.hamcoding.screendetox.data.firebase.entity.User
import com.hamcoding.screendetox.data.firebase.repository.RankRepository
import com.hamcoding.screendetox.data.firebase.repository.StatsRepository
import com.hamcoding.screendetox.data.firebase.repository.UserRepository
import com.hamcoding.screendetox.data.model.App
import com.hamcoding.screendetox.util.DateFormatText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.nio.file.Files.find
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val rankRepository: RankRepository,
    private val statsRepository: StatsRepository
) : ViewModel() {

    private val _rankingList = MutableLiveData<List<User>>()
    private val _rankNumber = MutableLiveData<String>("")
    private val _appList = MutableLiveData<List<App>>()

    val rankingList: LiveData<List<User>> = _rankingList
    val rankNumber: LiveData<String> get() = _rankNumber
    val appList: LiveData<List<App>> get() = _appList
    val totalUsage = statsRepository.getTotalTime()
    val todayDateText = DateFormatText.getCurrentDateShort()

    fun loadRankingList() {
        viewModelScope.launch {
            launch {
                _rankingList.value = rankRepository.loadRankingList()
            }.join()
            val userItem = _rankingList.value?.find {
                it.email == UserRepository.getUserEmail()
            }
            _rankNumber.value = rankingList.value?.indexOf(userItem)?.plus(1).toString()
        }
    }

    fun loadAppList() {
        _appList.value = statsRepository.getAppList()
    }
}