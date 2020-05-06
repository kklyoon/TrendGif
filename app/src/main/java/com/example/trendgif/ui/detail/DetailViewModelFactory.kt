package com.example.trendgif.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trendgif.entity.GifObject

class DetailViewModelFactory <T>(val gifObject: GifObject): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(gifObject) as T
        }
        throw IllegalArgumentException("Not DetailViewModel class")
    }

}