package com.example.trendgif.api

import androidx.paging.PageKeyedDataSource
import com.example.trendgif.entity.GifObject
import com.example.trendgif.util.Global
import com.example.trendgif.util.Logger
import io.reactivex.disposables.CompositeDisposable


class GiphySearchDataSource(private val giphyAPI: GiphyAPI,
                            private val compositeDisposable: CompositeDisposable,
                            private val searchKey: String
) : PageKeyedDataSource<Int, GifObject>() {

    val logger = Logger.getLogger(this.javaClass.simpleName)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GifObject>
    ) {
        logger.d("loadInitial")
        val curPage = 1
        val nextPage = curPage + 1
        compositeDisposable.add(giphyAPI.searchKeyword(searchKey).subscribe({ res ->
            logger.d("loadInitial size : ${res.dataList.size}, pagination: ${res.pagination}, meta : ${res.meta}")
            callback.onResult(res.dataList, null, nextPage)
        }, { throwable -> logger.e("${throwable.message}") }))
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GifObject>) {
        val nextKey = params.key + 1
        logger.d("loadAfter, Offset : ${nextKey * Global.GIPHY_PAGE_SIZE}  , count: ${params.requestedLoadSize}")
        compositeDisposable.add(giphyAPI.searchKeywordNext(searchKey, nextKey * Global.GIPHY_PAGE_SIZE).subscribe({ res ->
            logger.d("loadAfter size : ${res.dataList.size}, pagination: ${res.pagination}, meta : ${res.meta}")
            callback.onResult(res.dataList, nextKey)
        }, { throwable -> logger.d("${throwable.message}") }))

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GifObject>) {
    }

}