package com.example.trendgif.ui.gif

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.trendgif.R
import com.example.trendgif.databinding.FragmentGifBinding
import com.example.trendgif.util.Logger


class GifFragment : Fragment() {
    val logger = Logger.getLogger(this.javaClass.simpleName)

    private lateinit var gifViewModel: GifViewModel
    private lateinit var viewDataBinding: FragmentGifBinding
    private lateinit var gifItemAdapter: GifItemAdapter
    private val args: GifFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        gifViewModel = ViewModelProvider(activity as ViewModelStoreOwner, GifViewModelFactory<GifViewModel>(args.hashtag)).get(GifViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_gif, container, false)
        viewDataBinding.apply {
            vm = gifViewModel
            progressCircular.show()
        }
        setUpAdapter()
        return viewDataBinding.root
    }


    fun setUpAdapter(){
        val viewModel = viewDataBinding.vm
        //        val lm =
//            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val lm = GridLayoutManager(activity, 2)
        viewModel?.let{
            gifItemAdapter = GifItemAdapter(it)
            viewDataBinding.rvSearch.layoutManager = lm
            viewDataBinding.rvSearch.adapter = gifItemAdapter
            it.itemList.observe(viewLifecycleOwner, Observer { pagedList ->
                viewDataBinding.progressCircular.hide()
                gifItemAdapter.submitList(pagedList)
            })
        }
    }

}
