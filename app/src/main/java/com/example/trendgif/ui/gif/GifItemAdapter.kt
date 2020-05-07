package com.example.trendgif.ui.gif

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trendgif.BR
import com.example.trendgif.R
import com.example.trendgif.entity.GifObject

class GifItemAdapter(private val viewModel: GifViewModel): PagedListAdapter<GifObject, GifItemAdapter.GifViewHolder>(
    diffCallback) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_gif
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        return GifViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val item = getItem(position)
        item?.let{
            holder.binding.setVariable(BR.item, it)
            holder.binding.setVariable(BR.vm, viewModel)
        }
    }

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<GifObject>() {
            override fun areItemsTheSame(oldItem: GifObject, newItem: GifObject): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GifObject, newItem: GifObject): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

    inner class GifViewHolder: RecyclerView.ViewHolder {
        val binding: ViewDataBinding
        constructor(binding: ViewDataBinding): super(binding.root){
            this.binding = binding
        }
        constructor(parent: ViewGroup, layoutResId:Int): this(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutResId, parent, false)
        )
    }
}