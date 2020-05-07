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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.trendgif.R
import com.example.trendgif.databinding.FragmentGifBinding
import com.example.trendgif.entity.GifObject
import com.example.trendgif.util.EventObserver
import com.example.trendgif.util.Logger


class GifFragment : Fragment() {
    val logger = Logger.getLogger(this.javaClass.simpleName)

    private lateinit var gifViewModel: GifViewModel
    private lateinit var viewDataBinding: FragmentGifBinding
    private lateinit var gifItemAdapter: GifItemAdapter
    private val args: GifFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gifViewModel = ViewModelProvider(
            activity as ViewModelStoreOwner,
            GifViewModelFactory<GifViewModel>(args.hashtag)
        ).get(GifViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_gif, container, false)
        viewDataBinding.apply {
            vm = gifViewModel
            gifViewModel.setHashTag(args.hashtag)
            progressCircular.show()
        }
        setUpAdapter()
        return viewDataBinding.root
    }


    private fun setUpAdapter() {
        val viewModel = viewDataBinding.vm
        val lm = GridLayoutManager(activity, 2)
        viewModel?.let {
            gifItemAdapter = GifItemAdapter(it)
            viewDataBinding.rvSearch.layoutManager = lm
            viewDataBinding.rvSearch.adapter = gifItemAdapter
            it.itemList.observe(viewLifecycleOwner, Observer { pagedList ->
                viewDataBinding.progressCircular.hide()
                if (pagedList.size == 0) {
                    viewDataBinding.tvNoResult.visibility = View.VISIBLE
                    viewDataBinding.rvSearch.visibility = View.GONE
                    viewDataBinding.tvNoResult.text = String.format(
                        resources.getString(R.string.search_no_result),
                        args.hashtag
                    )
                } else gifItemAdapter.submitList(pagedList)
            })
            it.openDetailEvent.observe(viewLifecycleOwner, EventObserver {
                openGifDetails(it)
            })
        }
    }

    private fun openGifDetails(gifObject: GifObject) {
        val action = GifFragmentDirections.actionGifToDetail(gifObject)
        findNavController().navigate(action)
    }

}
