package com.hamcoding.screendetox.ui.rank

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hamcoding.screendetox.data.RankRepository
import com.hamcoding.screendetox.data.User
import com.hamcoding.screendetox.data.UserRepository
import kotlinx.coroutines.launch

class RankViewModel(private val rankRepository: RankRepository) : ViewModel() {

    val items = mutableListOf<User>()
    var rankNumber = 1

    fun loadFriendList() {
        items.clear()
        rankRepository.getFriendList()
        items.addAll(rankRepository.friendList)
        val userItem = items.find {
            it.email == UserRepository.getUserEmail()
        }
        rankNumber = items.indexOf(userItem) + 1
    }

    companion object {
        fun provideFactory(repository: RankRepository) = viewModelFactory {
            initializer {
                RankViewModel(repository)
            }
        }
    }

}