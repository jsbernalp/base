package com.jonathanbernal.libbase.list.items

const val DEFAULT_WEIGHT = 0

interface GenericCategoryItem<T : Any> : GenericItem<T> {

    val categoryName: String

    val weight: Int

    operator fun compareTo(categoryItem: GenericCategoryItem<*>): Int
}
