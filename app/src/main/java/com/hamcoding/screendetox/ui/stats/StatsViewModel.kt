package com.hamcoding.screendetox.ui.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamcoding.screendetox.data.App
import com.hamcoding.screendetox.data.AppRepository

class StatsViewModel(private val appRepository: AppRepository) : ViewModel() {

    private val _items = MutableLiveData<List<App>>()
    val items: LiveData<List<App>> = _items

    fun getAppList() {
        _items.value = appRepository.getAppList()
    }
}