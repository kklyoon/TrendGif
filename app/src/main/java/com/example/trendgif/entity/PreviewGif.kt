package com.example.trendgif.entity

import com.google.gson.annotations.SerializedName

/*
"preview_gif": {
                    "height": "62",
                    "size": "47513",
                    "url": "https://media3.giphy.com/media/kBI5aLB6wlw4zNnecN/giphy-preview.gif?cid=add3406aa28ae2261373d2d41b0b789aac6ec9019b5d1433&rid=giphy-preview.gif",
                    "width": "110"
                }
 */
data class PreviewGif(
    @SerializedName("height")
    val height: String,
    @SerializedName("size")
    val size: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: String
)