package com.example.trendgif.entity

import com.google.gson.annotations.SerializedName

//"locations": [
//{
//    "name": "Worldwide",
//    "woeid": 1
//}
//]
// korea : 1130853
// us : 2352824
data class Location (
    @SerializedName("name")
    val name: String,
    @SerializedName("woeid")
    val woeid: Int
)