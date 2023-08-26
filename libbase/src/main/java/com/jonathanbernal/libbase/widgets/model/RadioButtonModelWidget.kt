package com.jonathanbernal.libbase.widgets.model

import android.view.View
import androidx.annotation.DrawableRes
import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.widgets.actions.WidgetAction
import com.jonathanbernal.libbase.widgets.model.views.WidgetGravity

data class RadioButtonModelWidget(var text: CharSequence) {

    var fontSize = R.dimen.font_body

    var textColor = android.R.color.black

    var backgroundColor = android.R.color.white

    @DrawableRes
    var buttonTint: Int? = null

    var padding = SpacingSimpleTextView()

    var margin = SpacingSimpleTextView(R.dimen.spacing_empty)

    var gravity: WidgetGravity? = null

    var enable: Boolean = true

    var isChecked: Boolean = false

    var tag: Int = -1

    var resourceId: Int = 0
        private set

    var extras: Map<String, Any>? = null

    var textStyle: Int = 0

    var textAlignment: Int = View.TEXT_ALIGNMENT_TEXT_START

    var layoutDirection: Int = View.LAYOUT_DIRECTION_RTL

    var onCheckedAction: WidgetAction? = null

    constructor(resourceId: Int) : this("") {
        this.resourceId = resourceId
    }
}
