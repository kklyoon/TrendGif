package com.example.trendgif

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.trendgif.ui.trend.TrendFragmentDirections
import com.example.trendgif.util.Logger
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val logger = Logger.getLogger(this.javaClass.simpleName)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationUI.setupWithNavController(main_bottom_navigation, findNavController(R.id.nav_host_fragment))
        var woeid = resources.getInteger(R.integer.WORLD)
        main_bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_world -> woeid = resources.getInteger(R.integer.WORLD)
                R.id.navigation_kr -> woeid = resources.getInteger(R.integer.KR)
                R.id.navigation_us -> woeid = resources.getInteger(R.integer.US)
                else -> {}
            }
            val action = NaviGraphDirections.actionTrend(woeid)
            findNavController(R.id.nav_host_fragment).navigate(action)
            true
        }
    }
}
