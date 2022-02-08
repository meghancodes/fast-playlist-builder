package com.example.fastplaylistbuilder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

interface ListAdapterItem {
    var title : String?
}

// inspired by: https://medium.com/huawei-developers/how-to-use-recyclerview-with-databinding-mvvm-211f6b69a81a

class BaseViewHolder<binding: ViewDataBinding>(val binder: binding) :
    RecyclerView.ViewHolder(binder.root)

abstract class BaseAdapter<binding: ViewDataBinding, T: ListAdapterItem>(var data: List<T>) :
    RecyclerView.Adapter<BaseViewHolder<binding>>() {

    @get:LayoutRes
    abstract val layoutId: Int

    abstract fun bind(binding: binding, item: T)

    fun updateData(list: List<T>) {
        this.data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<binding> {
        val binder = DataBindingUtil.inflate<binding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )

        return BaseViewHolder(binder)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<binding>, position: Int) {
        bind(holder.binder, data[position])
    }

    override fun getItemCount(): Int = data.size

}