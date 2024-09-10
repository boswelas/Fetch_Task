package com.example.fetchtask.utils

import com.example.fetchtask.models.FetchItemsItem

object ItemUtils {

    fun groupAndFilterItems(fetchItems: List<FetchItemsItem>): Map<Int, List<FetchItemsItem>> {

        val filteredItems = fetchItems.filter {
            !it.name.isNullOrBlank()
        }


        return filteredItems.groupBy { it.listId }
    }
}
