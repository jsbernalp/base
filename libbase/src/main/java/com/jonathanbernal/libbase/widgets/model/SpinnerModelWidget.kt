package com.jonathanbernal.libbase.widgets.model

import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.list.factories.WIDGET_SPINNER
import com.jonathanbernal.libbase.widgets.model.views.BaseModelWidget
import com.jonathanbernal.libbase.widgets.model.views.WRAP_PARENT_VALUE

class SpinnerModelWidget(
    val id: String,
    val text: String? = null,
    val hint: String? = null,
    val colorHint: Int? = null,
    val backgroundColor: Int? = null,
    val selectElement: String? = null,
    val elements: List<ElementSpinner>?,
    widthRes: Int = WRAP_PARENT_VALUE,
    heightRes: Int = WRAP_PARENT_VALUE,
    padding: SpacingSimpleTextView = SpacingSimpleTextView(),
    margin: SpacingSimpleTextView = SpacingSimpleTextView(R.dimen.spacing_empty),
) : BaseModelWidget(
    width = widthRes,
    height = heightRes,
    padding = padding,
    margin = margin,
) {
    override fun getId(): Int = WIDGET_SPINNER
}

data class ElementSpinner(
    val id: String,
    val name: String
)
