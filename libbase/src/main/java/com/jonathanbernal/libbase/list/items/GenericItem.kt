package com.jonathanbernal.libbase.list.items

interface GenericItem<T : Any> {
    val data: T
    val type: Int
}
