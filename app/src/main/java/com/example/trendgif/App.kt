package com.example.trendgif

import android.app.Application
import com.example.trendgif.util.Global

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Global.setGIPHY_KEY(resources.getString(R.string.GIPHY_API_KEY))
        Global.setTwt_KEY(resources.getString(R.string.consumer_key), resources.getString(R.string.consumer_secret),
        resources.getString(R.string.access_token), resources.getString(R.string.access_secret))
    }
}