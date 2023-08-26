package com.rappi.components.widgets.model.views

import android.graphics.Typeface
import androidx.annotation.DrawableRes
import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.list.factories.WIDGET_CHECK_BOX
import com.jonathanbernal.libbase.widgets.model.views.BaseModelWidget
import com.jonathanbernal.libbase.widgets.model.views.WidgetGravity
import com.rappi.components.widgets.actions.WidgetAction
import com.rappi.components.widgets.model.SpacingSimpleTextView

class CheckBoxModelWidget(var text: CharSequence) :
    BaseModelWidget(padding = SpacingSimpleTextView()) {

    var fontSize = R.dimen.font_body

    var textColor = android.R.color.black

    var backgroundColor = android.R.color.white

    @DrawableRes
    var buttonTint: Int? = null

    var gravity: WidgetGravity? = null

    var enable: Boolean = true

    var isChecked: Boolean = false

    var tag: Int = -1

    var resourceId: Int = 0
        private set

    var onCheckedAction: WidgetAction? = null

    var unCheckedAction: WidgetAction? = null

    var textStyle: Int = Typeface.NORMAL

    constructor(resourceId: Int) : this("") {
        this.resourceId = resourceId
    }

    override fun getId(): Int = WIDGET_CHECK_BOX
}
