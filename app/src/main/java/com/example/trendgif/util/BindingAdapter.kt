package com.example.trendgif.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.webp.decoder.WebpDrawable
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.trendgif.entity.GifObject
import com.example.trendgif.entity.Trend

@BindingAdapter("TweetVolume")
fun setTweetVolume(view: TextView, item: Trend){
    item.tweet_volume?.let{
        view.text = "tweet $it times."
    }?: run {view.text = "N/A"}
}

@BindingAdapter("SetPreview")
fun setPreview(view: ImageView, item: GifObject){
    // previewWebp
    val previewWebp = item.imagesData.previewWebp
    previewWebp.url.let{
        val crop = CenterCrop()
        Glide.with(view).load(it).optionalTransform(WebpDrawable::class.java, WebpDrawableTransformation(crop)).into(view)
    }
}

@BindingAdapter("SetOriginal")
fun setOriginal(view: ImageView, item: GifObject){
    val original = item.imagesData.original
    original.webp.let{
        val crop = CenterCrop()
        Glide.with(view).load(it).optionalTransform(WebpDrawable::class.java, WebpDrawableTransformation(crop)).into(view)
    }
}