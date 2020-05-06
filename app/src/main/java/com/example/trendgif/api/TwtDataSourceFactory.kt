package com.example.trendgif.api

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.trendgif.entity.Trend
import io.reactivex.disposables.CompositeDisposable

class TwtDataSourceFactory(private val compositeDisposable: CompositeDisposable, private val twtAPI: TwtAPI, private val woeid: String)
    : DataSource.Factory<Int, Trend>(){
    val sourceLiveData = MutableLiveData<TwtDataSource>()

    override fun create(): DataSource<Int, Trend> {
        val twtDataSource = TwtDataSource(twtAPI, compositeDisposable, woeid)
        sourceLiveData.postValue(twtDataSource)
        return twtDataSource
    }
}
