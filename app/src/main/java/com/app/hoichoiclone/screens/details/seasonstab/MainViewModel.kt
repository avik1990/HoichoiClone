package com.app.hoichoiclone.screens.details.seasonstab

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.hoichoiclone.screens.details.seasonstab.model.SeasonModel
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private val _albumListLiveData = MutableLiveData<List<SeasonModel>>()
    val albumListLiveData: LiveData<List<SeasonModel>> = _albumListLiveData

    fun getAlbumList() {
        viewModelScope.launch {
            //  val list = repository.getAlbumListRepo()

            // _albumListLiveData.value = list
            // if (list.isEmpty()) {

            // } else {
        }
    }
}
