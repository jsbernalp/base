package com.jonathanbernal.libbase.list.items

import com.jonathanbernal.libbase.list.items.DEFAULT_WEIGHT
import com.jonathanbernal.libbase.list.items.GenericCategoryItem

class GenericCategoryItemAbstract(
    override var data: Any,
    val category: CharSequence,
    override val type: Int = 0,
    override val weight: Int = 0,
) : GenericCategoryItem<Any> {

    override val categoryName: String
        get() = category.toString()

    override fun compareTo(categoryItem: GenericCategoryItem<*>): Int {
        return when (val helperWeight = weight.compareTo(categoryItem.weight)) {
            DEFAULT_WEIGHT -> categoryName.compareTo(categoryItem.categoryName)
            else -> helperWeight
        }
    }
}
