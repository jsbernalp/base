package com.jonathanbernal.libbase.widgets.model.views

import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.list.factories.WIDGET_BUTTON
import com.jonathanbernal.libbase.widgets.actions.WidgetAction
import com.jonathanbernal.libbase.widgets.model.SpacingSimpleTextView

class ButtonModelWidget(
    val enable: Boolean,
    val text: CharSequence?,
    var gravity: WidgetGravity? = null,
    val keyAction: String? = null,
    val widthRes: Int = -1,
    val heightRes: Int = -1,
    val backgroundColor: Int,
    padding: SpacingSimpleTextView = SpacingSimpleTextView(),
    margin: SpacingSimpleTextView = SpacingSimpleTextView(R.dimen.spacing_empty),
    extras: Map<String, Any>? = null,
    action: WidgetAction? = null,
) : BaseModelWidget(
    width = widthRes,
    height = heightRes,
    margin = margin,
    padding = padding,
    extras = extras,
    action = action,
) {
    override fun getId(): Int = WIDGET_BUTTON
}
