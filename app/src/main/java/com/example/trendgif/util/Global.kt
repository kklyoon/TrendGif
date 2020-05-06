package com.example.trendgif.util
/*
private val consumerKey: String,
    private val consumerSecret: String,
    private val accessToken: String,
    private val accessSecret: String,
 */
class Global {
    companion object{
        var GIPHY_API_KEY = "INVALID_KEY"
        const val GIPHY_PAGE_SIZE = 15
        var consumerKey = "INVALID_KEY"
        var consumerSecret = "INVALID_KEY"
        var accessToken = "INVALID_KEY"
        var accessSecret = "INVALID_KEY"

        fun setGIPHY_KEY(key: String){
            GIPHY_API_KEY = key
        }
        fun setTwt_KEY(consumerKey: String, consumerSecret: String, accessToken: String, accessSecret: String ){
            this.consumerKey = consumerKey
            this.consumerSecret = consumerSecret
            this.accessToken = accessToken
            this.accessSecret = accessSecret
        }

    }
}