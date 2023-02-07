package com.hamcoding.screendetox.ui.dialogs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamcoding.screendetox.data.DatabaseRepository
import kotlinx.coroutines.flow.collectLatest

class DialogRequestFriendViewModel : ViewModel() {

    private val dbRepository: DatabaseRepository = DatabaseRepository()

    private val _isCancel = MutableLiveData<Boolean>()
    private val _isSubmit = MutableLiveData<Boolean>()
    val _isInvalidEmail = MutableLiveData<Boolean>()

    val isCancel: LiveData<Boolean> = _isCancel
    val isSubmit: LiveData<Boolean> = _isSubmit
    val isInvalidEmail: LiveData<Boolean> = _isInvalidEmail

    val emailText = MutableLiveData<String>()

    fun onCancelClick() {
        _isCancel.value = true
    }

    fun onSubmitClick() {
        if (!emailText.value.isNullOrEmpty()) {
            if(dbRepository.requestFriend(emailText.value!!)) {
                _isSubmit.value = true
            } else {
                // TODO 친구 찾을 수 없는 경우 처리
                _isInvalidEmail.value = true
            }
        } else {
            _isInvalidEmail.value = true
        }
    }
}