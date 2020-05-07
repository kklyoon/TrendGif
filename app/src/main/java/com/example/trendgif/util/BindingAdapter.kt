package com.example.trendgif.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.trendgif.R
import com.example.trendgif.entity.GifObject
import com.example.trendgif.entity.Trend
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ProgressBarDrawable
import com.facebook.drawee.view.SimpleDraweeView
import java.text.DecimalFormat

@BindingAdapter("TweetVolume")
fun setTweetVolume(view: TextView, item: Trend){
    item.tweet_volume?.let{
        val dec = DecimalFormat("#,###").format(it)
        val str = view.context.resources.getString(R.string.tweet_volume)
        view.text = String.format(str, dec)
    }?: run {view.text = "N/A"}
}

@BindingAdapter("SetPreview")
fun setPreview(view: SimpleDraweeView, item: GifObject){
    val previewWebp = item.imagesData.previewWebp
    previewWebp?.url?.let{
        view.hierarchy.setProgressBarImage(ProgressBarDrawable())
        view.controller = Fresco.newDraweeControllerBuilder()
            .setUri(it)
            .setAutoPlayAnimations(true)
            .build()
    }
}

@BindingAdapter("SetOriginal")
fun setOriginal(view: SimpleDraweeView, item: GifObject){
    val original = item.imagesData.original
    original?.webp?.let{
        view.hierarchy.setProgressBarImage(ProgressBarDrawable())
        view.controller = Fresco.newDraweeControllerBuilder()
            .setUri(it)
            .setAutoPlayAnimations(true)
            .build()
    }
}

@BindingAdapter("LoadAvatar")
fun setAvatar(view: SimpleDraweeView, item: GifObject){
    item.user?.avator_url?.let{
        if(isGif(it)) {
            view.controller = Fresco.newDraweeControllerBuilder()
                .setUri(it)
                .setAutoPlayAnimations(true)
                .build()
        }
        else {
            view.setImageURI(it)
        }
    }
}

fun isGif(url: String): Boolean{
    val ext = url.takeLast(3)
    return ext == "gif"
}

@BindingAdapter("SetText")
fun setText(view:TextView, str: String?){
    if(str == "" || str == null) view.text = "N/A"
    else view.text = str
}