package com.example.trendgif.entity

import com.google.gson.annotations.SerializedName

data class GifsResponseData(
    @SerializedName("data") val dataList: List<GifObject>,
    @SerializedName("pagination") val pagination: Pagination,
    @SerializedName("meta") val meta: Meta
)