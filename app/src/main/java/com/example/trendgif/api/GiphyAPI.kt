package com.example.trendgif.api

import com.example.trendgif.entity.GifsResponseData
import com.example.trendgif.util.Global
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GiphyAPI {

    @GET("gifs/search")
    fun searchKeyword(@Query("q") keyword: String, @Query("limit") limit: Int = Global.GIPHY_PAGE_SIZE, @Header("api_key") key: String = Global.GIPHY_API_KEY): Single<GifsResponseData>

    @GET("gifs/search")
    fun searchKeywordNext(@Query("q") keyword: String, @Query("offset") page: Int, @Query("limit") limit: Int = Global.GIPHY_PAGE_SIZE, @Header("api_key") key: String = Global.GIPHY_API_KEY): Single<GifsResponseData>

    companion object {

        fun getApi(): GiphyAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.giphy.com/v1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(GiphyAPI::class.java)
        }
    }
}