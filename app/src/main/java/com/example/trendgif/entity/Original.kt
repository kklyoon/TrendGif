package com.example.trendgif.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/*
                "original": {
                    "frames": "37",
                    "hash": "2e8a5cd263987dd6b93b6f3dec6337d9",
                    "height": "270",
                    "mp4": "https://media3.giphy.com/media/kBI5aLB6wlw4zNnecN/giphy.mp4?cid=add3406aa28ae2261373d2d41b0b789aac6ec9019b5d1433&rid=giphy.mp4",
                    "mp4_size": "98748",
                    "size": "1046276",
                    "url": "https://media3.giphy.com/media/kBI5aLB6wlw4zNnecN/giphy.gif?cid=add3406aa28ae2261373d2d41b0b789aac6ec9019b5d1433&rid=giphy.gif",
                    "webp": "https://media3.giphy.com/media/kBI5aLB6wlw4zNnecN/giphy.webp?cid=add3406aa28ae2261373d2d41b0b789aac6ec9019b5d1433&rid=giphy.webp",
                    "webp_size": "202078",
                    "width": "480"
                }
 */
@Parcelize
data class Original(
    @SerializedName("frames")
    val frames: String,
    @SerializedName("hash")
    val hash: String,
    @SerializedName("height")
    val height: String,
    @SerializedName("mp4")
    val mp4: String,
    @SerializedName("mp4_size")
    val mp4_size: String,
    @SerializedName("size")
    val size: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("webp")
    val webp: String?,
    @SerializedName("webp_size")
    val webp_size: String,
    @SerializedName("width")
    val width: String
): Parcelable