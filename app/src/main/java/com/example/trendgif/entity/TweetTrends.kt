package com.example.trendgif.entity

import com.google.gson.annotations.SerializedName

class TweetTrends (
    @SerializedName("trends")
    val trends:List<Trend>,
    @SerializedName("as_of")
    val as_of:String,
    @SerializedName("created_at")
    val created_at:String,
    @SerializedName("locations")
    val locations:List<Location>
)