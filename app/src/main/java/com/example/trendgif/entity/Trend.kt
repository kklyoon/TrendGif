package com.example.trendgif.entity

import com.google.gson.annotations.SerializedName
/*
 "name": "#MayThe4thBeWithYou",
                "url": "http://twitter.com/search?q=%23MayThe4thBeWithYou",
                "promoted_content": null,
                "query": "%23MayThe4thBeWithYou",
                "tweet_volume": 361433
 */
data class Trend(
    @SerializedName("name")
    val name:String,
    @SerializedName("url")
    val url:String,
    @SerializedName("promoted_content")
    val promoted_content:String,
    @SerializedName("query")
    val query:String,
    @SerializedName("tweet_volume")
    val tweet_volume:Long

)