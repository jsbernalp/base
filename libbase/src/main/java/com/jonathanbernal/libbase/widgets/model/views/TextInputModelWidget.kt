package com.jonathanbernal.libbase.widgets.model.views

import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.list.factories.WIDGET_INPUT
import com.jonathanbernal.libbase.widgets.actions.WidgetAction
import com.jonathanbernal.libbase.widgets.model.DrawableTextModel
import com.jonathanbernal.libbase.widgets.model.SpacingBoxCornerRadius
import com.jonathanbernal.libbase.widgets.model.SpacingSimpleTextView

class TextInputModelWidget(
    var id: String,
    var text: String? = null,
    var hint: String? = null,
    var backgroundColor: Int? = null,
    var boxStrokeColor: Int? = null,
    var tag: String? = null,
    var textColorHint: Int? = null,
    var boxCornerRadius: SpacingBoxCornerRadius? = null,
    var focusable: Boolean = true,
    var drawable: DrawableTextModel = DrawableTextModel(),
    var type: InputTypeOption? = null,
    val maxLength: Int? = null,
    widthRes: Int = -2,
    heightRes: Int = -2,
    padding: SpacingSimpleTextView = SpacingSimpleTextView(),
    margin: SpacingSimpleTextView = SpacingSimpleTextView(R.dimen.spacing_empty),
    action: WidgetAction? = null,
) : BaseModelWidget(
    width = widthRes,
    height = heightRes,
    padding = padding,
    margin = margin,
    action = action
) {
    override fun getId(): Int = WIDGET_INPUT
}

enum class InputTypeOption {
    DEFAULT, TELEPHONE, EMAIL, TEXT, PASSWORD, ALPHANUMERIC
}
