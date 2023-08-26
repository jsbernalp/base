package com.jonathanbernal.libbase.list.items

import android.view.View
import com.jonathanbernal.libbase.widgets.listeners.OnSingleClickListener

interface GenericItemView<T> {

    fun getData(): T?

    fun bind(item: T)

    fun binds(item: Any) = bind(item as T)

    fun setSelected(isSelected: Boolean)

    fun getView(): View? = null

    /**
     * Item Listener
     */

    fun setOnClickListener(function: (data: T) -> Unit) {
        getView()?.setOnClickListener(OnSingleClickListener { getData()?.let { function.invoke(it) } })
    }

    fun setOnItemListener(function: (view: View) -> Unit) {
        getView()?.apply {
            setOnClickListener(OnSingleClickListener { function.invoke(this) })
        }
    }
}
