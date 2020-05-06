package com.example.trendgif.ui.gif

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GifViewModelFactory <T>(val hashTag: String): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GifViewModel::class.java)) {
            return GifViewModel(hashTag) as T
        }
        throw IllegalArgumentException("Not GifViewModel class")
    }
}