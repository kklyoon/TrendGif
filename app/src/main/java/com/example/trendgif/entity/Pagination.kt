package com.example.trendgif.entity

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("offset")
    val offset:Int,
    @SerializedName("total_count")
    val total_count:Int,
    @SerializedName("count")
    val count:Int
)
