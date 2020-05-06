package com.example.trendgif.api

import androidx.paging.PageKeyedDataSource
import androidx.paging.PositionalDataSource
import com.example.trendgif.entity.Trend
import com.example.trendgif.util.Logger
import io.reactivex.disposables.CompositeDisposable

class TwtDataSource(
    private val twtAPI: TwtAPI,
    private val compositeDisposable: CompositeDisposable,
    private val woeid: String
) : PositionalDataSource<Trend>(){
    val logger = Logger.getLogger((this.javaClass.simpleName))
    var id = woeid

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Trend>) {
        logger.d("loadInit")
        compositeDisposable.add(twtAPI.getTrend(woeid).subscribe({ res->
            logger.d("loadInit size: ${res[0].trends.size}")
            callback.onResult(res[0].trends, 0)
        }, { throwable -> logger.e("${throwable.message}")}))
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Trend>) {
    }
}