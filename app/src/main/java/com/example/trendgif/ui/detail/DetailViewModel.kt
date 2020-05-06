package com.example.trendgif.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trendgif.entity.GifObject
import com.example.trendgif.util.Logger

class DetailViewModel(val item: GifObject) : ViewModel() {
    val logger = Logger.getLogger(this.javaClass.simpleName)

    private val _gifItem = MutableLiveData<GifObject>(item)
    val gifItem: LiveData<GifObject> = _gifItem

    fun setGif(gifObject: GifObject) {
        _gifItem.postValue(gifObject)
    }
}
