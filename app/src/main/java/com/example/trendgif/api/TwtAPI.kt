package com.example.trendgif.api

import com.example.trendgif.BuildConfig
import com.example.trendgif.entity.TweetTrends
import com.example.trendgif.util.Global
import com.example.trendgif.util.Oauth1SigningInterceptor
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface TwtAPI {

    @GET("trends/place.json")
    fun getTrend(@Query("id") id:String): Single<List<TweetTrends>>

    companion object{
        fun getApi():TwtAPI{
            val oauth1SigningInterceptor = Oauth1SigningInterceptor.Builder()
                .consumerKey(Global.consumerKey)
                .consumerSecret(Global.consumerSecret)
                .accessToken(Global.accessToken)
                .accessSecret(Global.accessSecret).build()
            val client = OkHttpClient.Builder().addInterceptor(oauth1SigningInterceptor).build()
//            client.networkInterceptors().add(oauth1SigningInterceptor)
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.twitter.com/1.1/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(TwtAPI::class.java)
        }
    }
}