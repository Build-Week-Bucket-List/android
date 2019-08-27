package com.rybarstudios.bucketlist.activity.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is photo gallery Fragment"
    }
    val text: LiveData<String> = _text
}