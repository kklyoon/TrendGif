package com.example.trendgif.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.navArgs

import com.example.trendgif.R
import com.example.trendgif.databinding.FragmentDetailBinding
import com.example.trendgif.util.Logger

class DetailFragment : Fragment() {

    val logger = Logger.getLogger(this.javaClass.simpleName)

    private lateinit var viewDataBinding: FragmentDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel =
            ViewModelProvider(this as ViewModelStoreOwner, DetailViewModelFactory<DetailViewModel>(args.gifObject)).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        viewDataBinding.apply {
            vm = detailViewModel
            detailViewModel.setGif(args.gifObject)
        }
        return viewDataBinding.root
    }

}
