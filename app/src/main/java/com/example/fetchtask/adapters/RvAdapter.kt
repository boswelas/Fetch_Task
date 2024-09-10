package com.example.fetchtask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchtask.databinding.ItemLayoutBinding
import com.example.fetchtask.models.FetchItemsItem

class RvAdapter (private val fetchItemsList: List<FetchItemsItem>): RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return fetchItemsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = fetchItemsList[position]
        holder.binding.apply {
            tvName.text = currentItem.name
        }
    }
}
