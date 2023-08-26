package com.jonathanbernal.libbase.list.items.helpers

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatTextView
import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.list.items.GenericItemView

open class DefaultCategory @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), GenericItemView<CharSequence> {

    private var category: CharSequence = ""

    init {
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val padding =
            java.lang.Float.valueOf(resources.getDimension(R.dimen.spacing_medium)).toInt()
        gravity = Gravity.CENTER_HORIZONTAL
        this.setPadding(padding, padding, padding, padding)
        isAllCaps = true
        layoutParams = params
    }

    override fun bind(item: CharSequence) {
        this.category = item
        text = item
    }

    override fun getData(): CharSequence {
        return category
    }

    override fun getView(): View? {
        return this
    }
}
