package com.example.fetchtask.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fetchtask.R
import com.example.fetchtask.databinding.ItemLayoutBinding
import com.example.fetchtask.models.FetchItemsItem

class RvAdapter(private val fetchItemsMap: Map<Int, List<FetchItemsItem>>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    private val expandedListIds = mutableSetOf<Int>()
    private val sortedListIds = fetchItemsMap.keys.sorted()

    inner class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val listId = sortedListIds[adapterPosition]
                if (expandedListIds.contains(listId)) {
                    expandedListIds.remove(listId)
                    binding.contentContainer.visibility = View.GONE
                } else {
                    expandedListIds.add(listId)
                    binding.contentContainer.visibility = View.VISIBLE
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listId = sortedListIds[position]
        holder.binding.tvHeader.text = "List ID: $listId"

        val items = fetchItemsMap[listId] ?: emptyList()

        val sortedItems = items.sortedBy {
            it.name.removePrefix("Item ").toIntOrNull() ?: Int.MAX_VALUE
        }

        holder.binding.contentContainer.removeAllViews()
        sortedItems.forEach { item ->
            val nameView = LayoutInflater.from(holder.binding.root.context)
                .inflate(R.layout.item_name, holder.binding.contentContainer, false) as TextView
            nameView.text = item.name
            holder.binding.contentContainer.addView(nameView)
        }

        val isExpanded = expandedListIds.contains(listId)
        holder.binding.contentContainer.visibility = if (isExpanded) View.VISIBLE else View.GONE
        val arrowIconRes = if (isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
        holder.binding.arrowIcon.setImageResource(arrowIconRes)
    }


    override fun getItemCount(): Int {
        return sortedListIds.size
    }
}
