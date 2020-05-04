package com.example.trendgif.util

class Global {
    companion object{
        var GIPHY_API_KEY = "INVALID_KEY"

        fun setGIPHY_KEY(key: String){
            GIPHY_API_KEY = key
        }

        val PAGE_SIZE = 15
    }
}