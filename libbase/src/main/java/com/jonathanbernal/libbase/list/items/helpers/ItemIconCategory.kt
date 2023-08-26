package com.jonathanbernal.libbase.list.items.helpers

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.list.items.GenericItemView

class ItemIconCategory @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) :
    AppCompatTextView(context, attrs, defStyleAttr), GenericItemView<IconCategoryModel> {

    private lateinit var categoryInfo: IconCategoryModel

    init {
        val spacingLarge = resources.getDimensionPixelSize(R.dimen.spacing_large)
        val params = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.MATCH_PARENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER_VERTICAL
            setMargins(0, spacingLarge, 0, spacingLarge)
        }
        this.setPadding(spacingLarge, spacingLarge, spacingLarge, spacingLarge)
        compoundDrawablePadding = resources.getDimensionPixelSize(R.dimen.spacing_medium)
        layoutParams = params
    }

    override fun getData(): IconCategoryModel {
        return categoryInfo
    }

    override fun bind(item: IconCategoryModel) {
        this.categoryInfo = item
        text = item.text
        setCompoundDrawablesWithIntrinsicBounds(item.leftImage, 0, item.rightImage, 0)
        setBackgroundColor(ContextCompat.getColor(context, item.backgroundColor))
    }

    override fun getView(): View? {
        return this
    }
}
