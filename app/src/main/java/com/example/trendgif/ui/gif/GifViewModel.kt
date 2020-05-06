package com.example.trendgif.ui.gif

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.trendgif.api.GiphyAPI
import com.example.trendgif.api.GiphySearchDataSourceFactory
import com.example.trendgif.entity.GifObject
import com.example.trendgif.util.Global
import io.reactivex.disposables.CompositeDisposable

class GifViewModel(hashtag: String) : ViewModel() {

    val sourceFactory: GiphySearchDataSourceFactory

    private val _itemList = MutableLiveData<PagedList<GifObject>>()
    var itemList: LiveData<PagedList<GifObject>> = _itemList

    private val compositeDisposable = CompositeDisposable()

    init {
        sourceFactory =
            GiphySearchDataSourceFactory(compositeDisposable, hashtag, GiphyAPI.getApi())
        val config = PagedList.Config.Builder().setPageSize(Global.GIPHY_PAGE_SIZE).build()
        itemList = LivePagedListBuilder(sourceFactory, config).build()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
