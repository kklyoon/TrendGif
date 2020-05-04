package com.example.trendgif.entity

import com.google.gson.annotations.SerializedName

data class GifObject(
    @SerializedName("type")
    val type: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("images")
    val imagesData: Images,
    @SerializedName("user")
    val user: User,
    @SerializedName("username")
    val username: String,
    @SerializedName("source")
    val source: String
)