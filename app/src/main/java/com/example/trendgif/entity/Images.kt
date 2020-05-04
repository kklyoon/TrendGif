package com.example.trendgif.entity

import com.google.gson.annotations.SerializedName

data class Images (
    @SerializedName("preview_gif")
    val previewGif: PreviewGif,
    @SerializedName("preview_webp")
    val previewWebp: PreviewGif,
    @SerializedName("original")
    val original: Original
)