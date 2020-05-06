package com.example.trendgif.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.webp.decoder.WebpDrawable
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation
import com.bumptech.glide.load.resource.bitmap.*
import com.example.trendgif.R
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
    val previewWebp = item.imagesData.previewGif
//    val previewWebp = item.imagesData.previewWebp
    previewWebp?.url?.let{
        Glide.with(view).asGif().load(it).centerCrop().into(view)
//        val crop = CenterCrop()
//        Glide.with(view).load(it).optionalTransform(WebpDrawable::class.java, WebpDrawableTransformation(crop)).into(view)
    }
}

@BindingAdapter("SetOriginal")
fun setOriginal(view: ImageView, item: GifObject){
    val original = item.imagesData.original
    original?.webp?.let{
        val crop = FitCenter()
        Glide.with(view).load(it).optionalTransform(WebpDrawable::class.java, WebpDrawableTransformation(crop)).into(view)
    }
}

@BindingAdapter("LoadAvatar")
fun setAvatar(view: ImageView, url: String?){
    url?.let{
        if(isGif(url))
            Glide.with(view).asGif().load(url).centerCrop().into(view)
        else
            Glide.with(view).load(url).centerCrop().into(view)
    } ?: run {
        Glide.with(view).load(R.drawable.ic_public_white_24dp).into(view)
    }
}

fun isGif(url: String): Boolean{
    val ext = url.takeLast(3)
    return ext == "gif"
}