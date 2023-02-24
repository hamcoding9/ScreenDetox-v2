package com.hamcoding.screendetox.ui.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamcoding.screendetox.data.db.repository.DatabaseRepository
import kotlinx.coroutines.launch

class DialogRequestFriendViewModel : ViewModel() {

    private val dbRepository: DatabaseRepository = DatabaseRepository()

    private val _isCancel = MutableLiveData<Boolean>()
    private val _isSubmit = MutableLiveData<Boolean>()
    private val _isInvalidEmail = MutableLiveData<Boolean>()

    val isCancel: LiveData<Boolean> = _isCancel
    val isSubmit: LiveData<Boolean> = _isSubmit
    val isInvalidEmail: LiveData<Boolean> = _isInvalidEmail

    val emailText = MutableLiveData<String>()

    fun onCancelClick() {
        _isCancel.value = true
    }

    fun onSubmitClick() {
        viewModelScope.launch {
            dbRepository.requestFriend(emailText.value!!)
            viewModelScope.launch {
                dbRepository.isEmailNotExist.collect { isEmailNotExist ->
                    if (isEmailNotExist) {
                        _isInvalidEmail.value = true
                    }
                }
            }
            dbRepository.isEmailExist.collect { isEmailExist ->
                if (isEmailExist) {
                    _isSubmit.value = true
                }
            }
        }
    }
}