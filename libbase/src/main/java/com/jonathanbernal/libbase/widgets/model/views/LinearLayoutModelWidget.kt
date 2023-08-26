package com.jonathanbernal.libbase.widgets.model.views

import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.list.factories.WIDGET_LINEAR_LAYOUT
import com.rappi.components.widgets.model.SpacingSimpleTextView

class LinearLayoutModelWidget(
    val orientation: String,
    val widgets: List<Pair<Any, Int>?>,
    var widthRes: Int = -1,
    var heightRes: Int = -2,
    var backgroundColor: Int? = null,
    val visibility: Boolean = true,
    val weightSum: Int? = null,
    var gravity: WidgetGravity? = null,
    extras: Map<String, Any>? = null,
    padding: SpacingSimpleTextView = SpacingSimpleTextView(),
    margin: SpacingSimpleTextView = SpacingSimpleTextView(R.dimen.spacing_empty),
) : BaseModelWidget(
    width = widthRes,
    height = heightRes,
    padding = padding,
    margin = margin,
    extras = extras,
) {
    override fun getId(): Int = WIDGET_LINEAR_LAYOUT
}

enum class WidgetOrientation(val key: String, val value: Int) {
    HORIZONTAL("HORIZONTAL", 0),
    VERTICAL("VERTICAL", 1),
}
