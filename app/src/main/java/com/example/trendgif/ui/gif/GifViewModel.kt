package com.example.trendgif.ui.gif

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.trendgif.api.GiphyAPI
import com.example.trendgif.api.GiphySearchDataSourceFactory
import com.example.trendgif.entity.GifObject
import com.example.trendgif.util.Event
import com.example.trendgif.util.Global
import com.example.trendgif.util.Logger
import io.reactivex.disposables.CompositeDisposable

class GifViewModel(hashtag: String) : ViewModel() {
    val logger = Logger.getLogger(this.javaClass.simpleName)

    var sourceFactory: GiphySearchDataSourceFactory

    private val _itemList = MutableLiveData<PagedList<GifObject>>()
    var itemList: LiveData<PagedList<GifObject>> = _itemList
    private val _openDetailEvent = MutableLiveData<Event<GifObject>>()
    val openDetailEvent: LiveData<Event<GifObject>> = _openDetailEvent

    private val compositeDisposable = CompositeDisposable()
    private val config = PagedList.Config.Builder().setPageSize(Global.GIPHY_PAGE_SIZE).build()

    init {
        sourceFactory =
            GiphySearchDataSourceFactory(compositeDisposable, hashtag, GiphyAPI.getApi())
        itemList = LivePagedListBuilder(sourceFactory, config).build()
    }

    fun setHashTag(keyword: String){
        sourceFactory =
            GiphySearchDataSourceFactory(compositeDisposable, keyword, GiphyAPI.getApi())
        itemList = LivePagedListBuilder(sourceFactory, config).build()
    }

    fun openDetail(gifObject: GifObject){
        _openDetailEvent.value = Event(gifObject)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
