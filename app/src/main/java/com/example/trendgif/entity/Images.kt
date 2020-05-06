package com.example.trendgif.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Images (
    @SerializedName("preview_gif")
    val previewGif: PreviewGif,
    @SerializedName("preview_webp")
    val previewWebp: PreviewGif?,
    @SerializedName("original")
    val original: Original?
): Parcelable