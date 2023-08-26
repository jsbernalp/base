package com.jonathanbernal.libbase.list.items

import com.jonathanbernal.libbase.list.factories.GenericAdapterFactory.TYPE_CATEGORY

open class CategoryItem(
    override val data: CharSequence,
    override var weight: Int,
) : GenericCategoryItem<CharSequence> {

    override val type: Int
        get() = TYPE_CATEGORY

    override val categoryName: String
        get() = data.toString()

    override fun compareTo(categoryItem: GenericCategoryItem<*>): Int {
        return when (val helperWeight = weight.compareTo(categoryItem.weight)) {
            DEFAULT_WEIGHT -> categoryName.compareTo(categoryItem.categoryName)
            else -> helperWeight
        }
    }
}
