package com.example.trendgif.ui.trend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trendgif.BR
import com.example.trendgif.R
import com.example.trendgif.entity.Trend

class TrendAdapter(private val viewModel: TrendViewModel): PagedListAdapter<Trend, TrendAdapter.TrendViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_trend
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendViewHolder {
        return TrendViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: TrendViewHolder, position: Int) {
        val item = getItem(position)
        item?.let{
            item.name = item.name.replace("#", "")
            holder.binding.setVariable(BR.item, it)
            holder.binding.setVariable(BR.vm, viewModel)
            holder.binding.executePendingBindings()
        }
    }



    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Trend>() {
            override fun areItemsTheSame(oldItem: Trend, newItem: Trend): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Trend, newItem: Trend): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
    inner class TrendViewHolder: RecyclerView.ViewHolder{
        val binding: ViewDataBinding

        constructor(binding: ViewDataBinding): super(binding.root){
            this.binding = binding
        }
        constructor(parent: ViewGroup, layoutResId:Int):this(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutResId, parent, false)
        )
    }
}