package com.hamcoding.screendetox.ui.rank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hamcoding.screendetox.data.db.repository.RankRepository
import com.hamcoding.screendetox.data.db.entity.User
import com.hamcoding.screendetox.data.db.repository.UserRepository

class RankViewModel(private val rankRepository: RankRepository) : ViewModel() {

    val items = mutableListOf<User>()
    var rankNumber = 1

    fun loadFriendList() {
        items.clear()
        rankRepository.getFriendList()
        items.addAll(rankRepository.friendList)
        val userItem = items.find {
            it.email == "${UserRepository.getUserEmail()}(나)"
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