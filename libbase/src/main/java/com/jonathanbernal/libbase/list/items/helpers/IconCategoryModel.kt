package com.jonathanbernal.libbase.list.items.helpers

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class IconCategoryModel(
    val text: String,
    @DrawableRes val leftImage: Int = 0,
    @DrawableRes val rightImage: Int = 0,
    @ColorRes val backgroundColor: Int = 0
)
