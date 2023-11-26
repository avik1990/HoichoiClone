package com.app.hoichoiclone.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val tabs = mutableListOf<String>("All", "Movies", "Shows", "Watchlist")
    private var _pictureListLive: MutableLiveData<MutableList<String>> = MutableLiveData()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun getTabsData(): LiveData<MutableList<String>> {
        _pictureListLive.value = tabs
        return _pictureListLive
    }
}
