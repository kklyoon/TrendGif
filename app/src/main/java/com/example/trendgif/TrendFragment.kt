package com.example.trendgif

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.trendgif.databinding.FragmentTrendBinding
import com.example.trendgif.util.Logger


class TrendFragment : Fragment() {
    val logger = Logger.getLogger(this.javaClass.simpleName)

    private lateinit var viewDataBinding: FragmentTrendBinding
    private lateinit var trendViewModel: TrendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trend, container, false)
        viewDataBinding.apply{
            viewModel = trendViewModel
        }

        return viewDataBinding.root
//        return inflater.inflate(R.layout.trend_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        trendViewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(TrendViewModel::class.java)
    }

}
