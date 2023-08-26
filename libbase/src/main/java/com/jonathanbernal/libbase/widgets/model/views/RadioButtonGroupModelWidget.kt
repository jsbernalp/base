package com.jonathanbernal.libbase.widgets.model.views

import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.list.factories.WIDGET_RADIO_BUTTON_GROUP
import com.jonathanbernal.libbase.widgets.model.SpacingSimpleTextView

class RadioButtonGroupModelWidget(
    padding: SpacingSimpleTextView = SpacingSimpleTextView(R.dimen.spacing_empty),
    margin: SpacingSimpleTextView = SpacingSimpleTextView(R.dimen.spacing_empty),
    val widgets: List<Pair<Any, Int>?>,
    val visibility: Boolean = true,
) : BaseModelWidget(
    padding = padding,
    margin = margin
) {

    var enable: Boolean = true

    var backgroundColor = android.R.color.white

    override fun getId(): Int = WIDGET_RADIO_BUTTON_GROUP
}
