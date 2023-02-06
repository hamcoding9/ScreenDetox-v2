package com.hamcoding.screendetox.data

import androidx.lifecycle.ViewModel

abstract class HomeViewModel : ViewModel() {
    abstract val name: String
    abstract val rank: String
    abstract val usageDuration: String
    abstract val date: String
}