package com.jonathanbernal.libbase.widgets.model.views

import android.content.Context
import android.view.Gravity
import androidx.lifecycle.MutableLiveData
import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.list.items.GenericItemAbstract
import com.rappi.components.widgets.actions.WidgetAction
import com.rappi.components.widgets.model.SpacingSimpleTextView

const val WIDGET_TYPE_TEXT = "text"
const val WIDGET_TYPE_IMAGE = "image"
const val WIDGET_TYPE_PROGRESS_BAR = "progress_bar"
const val WIDGET_TYPE_LINEAR_LAYOUT = "linear_layout"
const val WIDGET_TYPE_BUTTON = "button"

const val MATCH_PARENT_RES = "match_parent"
const val WRAP_PARENT_RES = "wrap_parent"
const val MATCH_PARENT_VALUE = -1
const val WRAP_PARENT_VALUE = -2
const val WEIGHT_RES = "weight"
const val LINE_EXTRA_ADD_DIMEN = "line_extra_add_dimen"

typealias FunctionContextScope = suspend (Context, MutableLiveData<BaseModelWidget>) -> Unit

abstract class BaseModelWidget(
    var padding: SpacingSimpleTextView = SpacingSimpleTextView(R.dimen.spacing_empty),
    var margin: SpacingSimpleTextView = SpacingSimpleTextView(R.dimen.spacing_empty),
    var width: Int = -1,
    var height: Int = -1,
    var extras: Map<String, Any>? = null,
    var action: WidgetAction? = null,
    var functionScopeContext: FunctionContextScope? = null,
) {

    /**
     * Functions
     */

    abstract fun getId(): Int
}

/**
 * Extensions
 */

fun BaseModelWidget.wrapContent() = this.apply {
    width = -2
    height = -2
}

/**
 * Wrappers
 */

fun BaseModelWidget.intoContainer(): LinearLayoutModelWidget =
    LinearLayoutModelWidget(
        widthRes = MATCH_PARENT_VALUE,
        heightRes = WRAP_PARENT_VALUE,
        orientation = WidgetOrientation.VERTICAL.key,
        widgets = listOf(this.toPairItem())
    )

fun BaseModelWidget.horizontalConcat(widget: BaseModelWidget) = LinearLayoutModelWidget(
    widthRes = MATCH_PARENT_VALUE,
    heightRes = WRAP_PARENT_VALUE,
    orientation = WidgetOrientation.HORIZONTAL.key,
    widgets = listOf(this.wrapContent().toPairItem(), widget.wrapContent().toPairItem())
)

fun BaseModelWidget.verticalConcat(
    widget: BaseModelWidget,
) = LinearLayoutModelWidget(
    widthRes = MATCH_PARENT_VALUE,
    heightRes = WRAP_PARENT_VALUE,
    orientation = WidgetOrientation.VERTICAL.key,
    widgets = listOf(this.toPairItem(), widget.toPairItem()),
)

enum class WidgetGravity(val type: String) {
    TOP("top"),
    BOTTOM("bottom"),
    CENTER("center"),
    CENTER_HORIZONTAL("center_horizontal"),
    CENTER_VERTICAL("center_vertical"),
    LEFT("left"),
    RIGHT("right"),
}

fun WidgetGravity.getGravityView(): Int? = when (this) {
    WidgetGravity.TOP -> Gravity.TOP
    WidgetGravity.BOTTOM -> Gravity.BOTTOM
    WidgetGravity.CENTER -> Gravity.CENTER
    WidgetGravity.CENTER_HORIZONTAL -> Gravity.CENTER_HORIZONTAL
    WidgetGravity.CENTER_VERTICAL -> Gravity.CENTER_VERTICAL
    WidgetGravity.LEFT -> Gravity.LEFT
    WidgetGravity.RIGHT -> Gravity.RIGHT
}

/**
 * Transform
 */

fun BaseModelWidget.toPairItem() = Pair(this, getId())

fun BaseModelWidget.toGenericItem() = GenericItemAbstract(this, getId())
