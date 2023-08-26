package com.jonathanbernal.libbase.list.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jonathanbernal.libbase.list.factories.GenericAdapterFactory
import com.jonathanbernal.libbase.list.items.CategoryItem
import com.jonathanbernal.libbase.list.items.GenericCategoryItem
import com.jonathanbernal.libbase.list.items.GenericItem
import com.jonathanbernal.libbase.list.items.GenericItemView

open class GenericAdapter(
    private val factory: GenericAdapterFactory,
    private var categoryEnable: Boolean = false,
    val defaultCategory: String = "Other",
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    constructor(factory: GenericAdapterFactory) : this(factory, false, "Other")

    protected var _items: MutableList<GenericItem<*>> = mutableListOf()

    val INDEX_NOT_FOUND = -1

    var items: List<GenericItem<*>>
        get() = _items.toList()
        set(value) {
            addItems(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val item: GenericItemView<*> = factory.onCreateViewHolder(parent, viewType)
        val view: View = item.getView() ?: item as View
        return ItemViewHolder(view, item)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder as ItemViewHolder
        view.itemModel.binds(getItem(position).data)
    }

    protected open fun getItem(position: Int): GenericItem<*> = this.items[position]

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    /**
     * Set Configuration
     */

    fun setCategory(categoryEnable: Boolean) {
        this.categoryEnable = categoryEnable
        notifyDataSetChanged()
    }

    /**
     * Set Items
     */

    private fun addNewItem(genericItem: GenericItem<*>) {
        if (categoryEnable && genericItem is GenericCategoryItem<*>) {
            addItemInCategory(genericItem)
        } else _items.add(genericItem)
    }

    private fun addItemInCategory(item: GenericCategoryItem<*>) {
        val categoryName = item.categoryName ?: defaultCategory
        val categoryIndex = indexOfCategory(categoryName)
        if (categoryIndex != INDEX_NOT_FOUND) _items.add((categoryIndex + 1), item)
        else _items.add(addCategoryItem(createCategoryItem(categoryName, item.weight)) + 1, item)
    }

    private fun indexOfCategory(categoryName: CharSequence): Int =
        items.indexOfLast {
            it is GenericCategoryItem && it.categoryName == categoryName
        }

    /**
     * Add Category
     */

    private fun addCategoryItem(categoryItem: GenericCategoryItem<*>): Int {
        var categoryIndex: Int =
            _items.indexOfFirst {
                it.type == GenericAdapterFactory.TYPE_CATEGORY && it is GenericCategoryItem && it >= categoryItem
            }
        categoryIndex = if (categoryIndex == INDEX_NOT_FOUND) _items.size else categoryIndex
        _items.add(categoryIndex, categoryItem)
        return categoryIndex
    }

    /**
     * Add Item
     */

    fun addItem(item: GenericItem<*>) {
        addNewItem(item)
        notifyDataSetChanged()
    }

    fun addItems(items: List<GenericItem<*>>) {
        items.forEach { addNewItem(it) }
        notifyDataSetChanged()
    }

    fun addItem(position: Int, genericItem: GenericItem<*>) {
        if (categoryEnable && genericItem is GenericCategoryItem<*>) {
            addItem(genericItem)
        } else {
            _items.add(position, genericItem)
            notifyItemInserted(position)
        }
    }

    /**
     * Remove Item
     */

    open fun remove(item: GenericItem<*>) {
        val index = items.indexOf(item)
        if (index != -1) {
            _items.removeAt(index)
            notifyItemRemoved(index)
            if (categoryEnable) removeEmptyCategory(index)
        }
    }

    fun removeItemAtPosition(position: Int) {
        if (position >= 0 && position < items.size) {
            _items.removeAt(position)
            notifyItemRemoved(position)
            if (categoryEnable) removeEmptyCategory(position)
        }
    }

    private fun removeEmptyCategory(index: Int) {
        var prevItem: GenericItem<*>? = null
        var nextItem: GenericItem<*>? = null
        if (index < items.size) {
            nextItem = items[index]
        }
        if (index > 0) {
            prevItem = items[index - 1]
        }

        if (prevItem != null && prevItem is CategoryItem && (nextItem == null || nextItem is CategoryItem)) {
            _items.removeAt(index - 1)
            notifyItemRemoved(index - 1)
        }
    }

    fun removeItemsOfCategory(category: String) {
        val iterator: MutableIterator<GenericItem<*>> = _items.iterator()
        var startRange = -1
        var endRange = -1
        var indexIterator = 0
        while (iterator.hasNext()) {
            val item = iterator.next()
            if ((item is GenericCategoryItem<*>) && item.categoryName == category) {
                iterator.remove()
                if (startRange == -1) startRange = indexIterator else endRange =
                    indexIterator
            }
            indexIterator++
        }
        notifyCategoryRemoved(startRange, endRange)
    }

    private fun notifyCategoryRemoved(startRange: Int, endRange: Int) {
        if (endRange != -1 && startRange != -1) {
            notifyItemRangeRemoved(startRange, endRange)
        } else if (startRange != -1) {
            notifyItemRemoved(startRange)
        }
    }

    /**
     * update
     */

    fun notifyItemWithDataChanged(data: Any) {
        val genericItem: GenericItem<*>? = getItemWithData(data)
        notifyItemChanged(getItemPosition(genericItem))
    }

    private fun getItemWithData(data: Any): GenericItem<*>? {
        val i = items.iterator()
        while (i.hasNext()) {
            val itemView = i.next()
            if (itemView.data === data) {
                return itemView
            }
        }
        return null
    }

    open fun getItemPosition(item: GenericItem<*>?): Int {
        var itemPosition = -1
        if (items.isNotEmpty()) {
            itemPosition = items.indexOf(item)
        }
        return itemPosition
    }

    /**
     * Clear
     */

    open fun clearAll() {
        _items.clear()
        notifyDataSetChanged()
    }

    /**
     * gets
     */

    open fun getItemAtPosition(position: Int): GenericItem<*>? {
        var genericItem: GenericItem<*>? = null
        if (items.size > position) {
            genericItem = getItem(position)
        }
        return genericItem
    }

    /**
     * Generic
     */

    open fun createCategoryItem(
        categoryName: CharSequence,
        weight: Int
    ): GenericCategoryItem<*> =
        CategoryItem(categoryName, weight)

    /**
     * Item Wrapper
     */

    private class ItemViewHolder(
        itemView: View,
        val itemModel: GenericItemView<*>,
    ) : RecyclerView.ViewHolder(itemView)
}
