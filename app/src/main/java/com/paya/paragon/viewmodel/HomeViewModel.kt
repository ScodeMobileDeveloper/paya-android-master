package com.paya.paragon.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.paya.paragon.base.BaseViewModel
import com.paya.paragon.model.Event

class HomeViewModel(application: Application): BaseViewModel(application) {


    private val _isPropertyTabClicked: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val isPropertyTabClicked : LiveData<Event<Boolean>>
        get() = _isPropertyTabClicked

    fun updatePropertyTabClickState() {
        _isPropertyTabClicked.value = Event(true)
    }
}