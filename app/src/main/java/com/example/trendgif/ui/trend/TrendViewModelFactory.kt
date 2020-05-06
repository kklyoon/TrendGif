package com.example.trendgif.ui.trend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TrendViewModelFactory<T>(var woeid: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TrendViewModel::class.java)) return TrendViewModel(woeid) as T
        throw IllegalArgumentException("Unkown ViewModel class")
    }
}