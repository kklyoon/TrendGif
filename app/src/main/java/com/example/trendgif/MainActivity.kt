package com.example.trendgif

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.trendgif.databinding.ActivityMainBinding
import com.example.trendgif.util.Logger

class MainActivity : AppCompatActivity() {
    val logger = Logger.getLogger(this.javaClass.simpleName)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this@MainActivity
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    fun bottomClick(view: View){
        when(view.id){
            R.id.btn_world -> logger.d("world")
            R.id.btn_kr -> logger.d("kr")
            R.id.btn_us -> logger.d("us")
        }
    }
}
