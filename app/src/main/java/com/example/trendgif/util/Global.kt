package com.example.trendgif.util

class Global {
    companion object{
        var GIPHY_API_KEY = "INVALID_KEY"
        const val GIPHY_PAGE_SIZE = 15
        var CONSUMER_KEY = "INVALID_KEY"
        var CONSUMER_SECRET = "INVALID_KEY"
        var ACCESS_TOKEN = "INVALID_KEY"
        var ACCESS_SECRET = "INVALID_KEY"

        fun setGIPHY_KEY(key: String){
            GIPHY_API_KEY = key
        }
        fun setTwt_KEY(consumerKey: String, consumerSecret: String, accessToken: String, accessSecret: String ){
            this.CONSUMER_KEY = consumerKey
            this.CONSUMER_SECRET = consumerSecret
            this.ACCESS_TOKEN = accessToken
            this.ACCESS_SECRET = accessSecret
        }

    }
}