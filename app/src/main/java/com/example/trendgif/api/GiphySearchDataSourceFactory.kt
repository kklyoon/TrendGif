package com.example.trendgif.api

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.trendgif.entity.GifObject
import io.reactivex.disposables.CompositeDisposable

class GiphySearchDataSourceFactory (private val compositeDisposable: CompositeDisposable,
                                    private val searchKey: String,
                                    private val giphyAPI: GiphyAPI
): DataSource.Factory<Int, GifObject>() {
    private val giphyDataSourceLiveData = MutableLiveData<GiphySearchDataSource>()

    override fun create(): DataSource<Int, GifObject> {
        val source = GiphySearchDataSource(giphyAPI, compositeDisposable, searchKey)
        giphyDataSourceLiveData.postValue(source)
        return source
    }
}