package com.example.trendgif

import android.app.Application
import com.example.trendgif.util.Global
import com.facebook.drawee.backends.pipeline.Fresco

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        Global.setGIPHY_KEY(resources.getString(R.string.GIPHY_API_KEY))
        Global.setTwt_KEY(resources.getString(R.string.consumer_key), resources.getString(R.string.consumer_secret),
        resources.getString(R.string.access_token), resources.getString(R.string.access_secret))
    }
}