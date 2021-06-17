package com.belsoft.windowsisetstest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _keyboardToggle = MutableLiveData<Boolean>()
    val keyboardToggle: LiveData<Boolean> = _keyboardToggle

    fun toggleKeyboard() {
        val value: Boolean = _keyboardToggle.value ?: false
        _keyboardToggle.value = !value
    }
}