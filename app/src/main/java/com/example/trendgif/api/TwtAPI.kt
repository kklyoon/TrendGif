package com.example.trendgif.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TwtAPI {

//https://api.twitter.com/1.1/trends/place.json?id=1130853
    @GET("trends/place.json")
    fun getTrend(@Query("id") id:String)

    companion object{
        fun getApi():TwtAPI{
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(TwtAPI::class.java)
        }
    }
}