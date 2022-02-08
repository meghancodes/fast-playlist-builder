package com.example.fastplaylistbuilder

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fastplaylistbuilder.adapters.BaseAdapter
import com.example.fastplaylistbuilder.adapters.ListAdapterItem

@BindingAdapter("adapter")
fun setAdapter(
    recyclerView: RecyclerView,
    adapter: BaseAdapter<ViewDataBinding, ListAdapterItem>?
) {
    adapter?.let { recyclerView.adapter = it }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("item_list")
fun setItemList(
    recyclerView: RecyclerView,
    list: List<ListAdapterItem>?
) {
    val adapter = recyclerView.adapter as BaseAdapter<ViewDataBinding, ListAdapterItem>?
    adapter?.updateData(list ?: listOf())
}

@BindingAdapter("imageUrl")
fun setImage(
    imageView: ImageView,
    imageUrl: String?)
{
    Glide.with(imageView.context)
        .load(imageUrl)
        .into(imageView)
    //Todo - add placeholder image for error state
}