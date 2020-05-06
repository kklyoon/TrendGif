package com.example.trendgif.ui.trend

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.trendgif.api.TwtAPI
import com.example.trendgif.api.TwtDataSource
import com.example.trendgif.api.TwtDataSourceFactory
import com.example.trendgif.entity.Trend
import com.example.trendgif.entity.TweetTrends
import com.example.trendgif.util.Event
import com.example.trendgif.util.Logger
import io.reactivex.disposables.CompositeDisposable

class TrendViewModel(val woeid:Int = 1) : ViewModel() {
    val logger = Logger.getLogger(this.javaClass.simpleName)

    private var twtDataSourceFactory: TwtDataSourceFactory
    private val _itemList = MutableLiveData<PagedList<Trend>>()
    var itemList: LiveData<PagedList<Trend>> = _itemList
    private val _searchGifEvent = MutableLiveData<Event<String>>()
    var searchGifEvent: LiveData<Event<String>> = _searchGifEvent
    private val config = PagedList.Config.Builder().setEnablePlaceholders(false).setPageSize(50).build()

    private val compositeDisposable = CompositeDisposable()
    init{
        twtDataSourceFactory = TwtDataSourceFactory(compositeDisposable, TwtAPI.getApi(), woeid.toString())
        itemList = LivePagedListBuilder(twtDataSourceFactory, config).build()
        logger.d("init : $woeid")
    }

    fun setWoeid(newWoeid: String){
        twtDataSourceFactory = TwtDataSourceFactory(compositeDisposable, TwtAPI.getApi(), newWoeid)
        itemList = LivePagedListBuilder(twtDataSourceFactory, config).build()
    }

    fun openSearch(name: String){
        _searchGifEvent.postValue(Event(name))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
