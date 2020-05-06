package com.example.trendgif.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.trendgif.entity.Trend

@BindingAdapter("TweetVolume")
fun setTweetVolume(view: TextView, item: Trend){
    item.tweet_volume?.let{
        view.text = "tweet $it times."
    }?: run {view.text = "N/A"}
}