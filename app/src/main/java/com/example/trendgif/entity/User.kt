package com.example.trendgif.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("avator_url")
    val avator_url: String,
    @SerializedName("banner_url")
    val banner_url: String,
    @SerializedName("profile_url")
    val profile_url: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("display_name")
    val display_name: String
): Parcelable