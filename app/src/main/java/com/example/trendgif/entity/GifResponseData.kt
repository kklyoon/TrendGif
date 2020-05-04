package com.example.trendgif.entity

import com.google.gson.annotations.SerializedName

data class GifResponseData (
    @SerializedName("data") val data: GifObject,
    @SerializedName("meta") val meta: Meta
)