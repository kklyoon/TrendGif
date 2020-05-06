package com.example.trendgif.ui.trend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.trendgif.R
import com.example.trendgif.databinding.FragmentTrendBinding
import com.example.trendgif.ui.gif.GifFragmentDirections
import com.example.trendgif.util.EventObserver
import com.example.trendgif.util.Logger


class TrendFragment : Fragment() {
    val logger = Logger.getLogger(this.javaClass.simpleName)

    private lateinit var viewDataBinding: FragmentTrendBinding
    private val args by navArgs<TrendFragmentArgs>()
    private lateinit var trendViewModel: TrendViewModel
    private lateinit var trendAdapter: TrendAdapter

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        trendViewModel = ViewModelProvider(activity as ViewModelStoreOwner, TrendViewModelFactory<TrendViewModel>(args.woeid)).get(TrendViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logger.d("onCreateView, woeid : ${args.woeid}")
        viewDataBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_trend, container, false)
        viewDataBinding.apply{
            viewModel = trendViewModel
            trendViewModel.setWoeid(args.woeid.toString())
            progressCircular.show()
        }
        setListAdapter()
        trendViewModel.searchGifEvent.observe(viewLifecycleOwner, EventObserver {
            openGifFragment(it)
        })
        return viewDataBinding.root
    }

    private fun setListAdapter(){
        val viewModel = viewDataBinding.viewModel

        viewModel?.let{
            trendAdapter = TrendAdapter(it)
            viewDataBinding.rvTrend.adapter = trendAdapter
            it.itemList.observe(viewLifecycleOwner, Observer { pagedList ->
                viewDataBinding.progressCircular.hide()
                trendAdapter.submitList(pagedList)
            })
        }
    }

    private fun openGifFragment(hashtag: String){
        logger.d("openGifFragment : ${hashtag}")
        val action = TrendFragmentDirections.actionTrendToGif(hashtag)
        findNavController().navigate(action)
    }
}
