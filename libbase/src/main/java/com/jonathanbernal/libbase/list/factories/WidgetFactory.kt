package com.jonathanbernal.libbase.list.factories

import android.content.Context
import android.view.ViewGroup
import com.jonathanbernal.libbase.list.items.GenericItemView
import com.jonathanbernal.libbase.widgets.model.RadioButtonModelWidget
import com.jonathanbernal.libbase.widgets.model.SpinnerModelWidget
import com.rappi.components.widgets.model.TextModelWidget
import com.jonathanbernal.libbase.widgets.model.views.ButtonModelWidget
import com.rappi.components.widgets.model.views.CheckBoxModelWidget
import com.jonathanbernal.libbase.widgets.model.views.ImageModelWidget
import com.jonathanbernal.libbase.widgets.model.views.LinearLayoutModelWidget
import com.jonathanbernal.libbase.widgets.model.views.RadioButtonGroupModelWidget
import com.jonathanbernal.libbase.widgets.model.views.TextInputModelWidget
import com.rappi.components.widgets.view.ButtonViewWidget
import com.rappi.components.widgets.view.CheckTextViewWidget
import com.rappi.components.widgets.view.ImageViewWidget
import com.rappi.components.widgets.view.LinearLayoutViewWidget
import com.jonathanbernal.libbase.widgets.view.RadioButtonGroupViewWidget
import com.jonathanbernal.libbase.widgets.view.RadioButtonViewWidget
import com.rappi.components.widgets.view.SpinnerOutlineWidget
import com.jonathanbernal.libbase.widgets.view.TextInputRappiWidget
import com.rappi.components.widgets.view.TextViewWidget

const val WIDGET_TEXT = 1009001
const val WIDGET_PROGRESS_BAR = 1009002
const val WIDGET_IMAGE_VIEW = 1009003
const val WIDGET_LINEAR_LAYOUT = 1009004
const val WIDGET_BUTTON = 1009006
const val WIDGET_INPUT_RAPPI = 1009007
const val WIDGET_SPINNER = 1009008
const val WIDGET_CHECK_BOX = 1009009
const val WIDGET_RADIO_BUTTON = 1009010
const val WIDGET_RADIO_BUTTON_GROUP = 1009011

open class WidgetFactory : GenericAdapterFactory {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericItemView<*> {
        return when (viewType) {
            WIDGET_TEXT -> TextViewWidget(parent.context).apply {
                setListener(onWidgetListener)
            }
            WIDGET_IMAGE_VIEW -> ImageViewWidget(parent.context)
            WIDGET_LINEAR_LAYOUT -> LinearLayoutViewWidget(parent.context, onWidgetListener)
            WIDGET_BUTTON -> ButtonViewWidget(parent.context)
            WIDGET_CHECK_BOX -> CheckTextViewWidget(parent.context).apply {
                setListener(onWidgetListener)
            }
            WIDGET_RADIO_BUTTON -> RadioButtonViewWidget(parent.context).apply {
                setListener(onWidgetListener)
            }
            WIDGET_RADIO_BUTTON_GROUP -> RadioButtonGroupViewWidget(parent.context).apply {
                setListener(onWidgetListener)
            }
            WIDGET_INPUT_RAPPI -> TextInputRappiWidget(parent.context).apply {
                setTextChangedListener(onWidgetListener)
            }
            WIDGET_SPINNER -> SpinnerOutlineWidget(parent.context).apply {
                setItemSelectedListener(onWidgetListener)
            }
            else -> getFactoryItem(parent, viewType)
        }
    }

    open fun getFactoryItem(parent: ViewGroup, viewType: Int): GenericItemView<*> =
        TextViewWidget(parent.context)

    open val onWidgetListener: (Any) -> Unit = {}

    fun onCreateViewForLinearWidget(
        context: Context,
        widget: Pair<Any, Int>,
        listenerItem: (Any) -> Unit = {},
    ): GenericItemView<*>? =
        when (widget.second) {
            WIDGET_TEXT -> TextViewWidget(context).apply {
                bind(widget.first as TextModelWidget)
                setListener(listenerItem)
            }
            WIDGET_IMAGE_VIEW -> ImageViewWidget(context).apply {
                bind(widget.first as ImageModelWidget)
                setListener(listenerItem)
            }
            WIDGET_LINEAR_LAYOUT -> LinearLayoutViewWidget(context, listenerItem).apply {
                bind(widget.first as LinearLayoutModelWidget)
            }
            WIDGET_BUTTON -> ButtonViewWidget(context).apply {
                bind(widget.first as ButtonModelWidget)
                setListener(listenerItem)
            }
            WIDGET_CHECK_BOX -> CheckTextViewWidget(context).apply {
                bind(widget.first as CheckBoxModelWidget)
                setListener(listenerItem)
            }
            WIDGET_RADIO_BUTTON -> RadioButtonViewWidget(context).apply {
                bind(widget.first as RadioButtonModelWidget)
                setListener(listenerItem)
            }
            WIDGET_RADIO_BUTTON_GROUP -> RadioButtonGroupViewWidget(context).apply {
                bind(widget.first as RadioButtonGroupModelWidget)
                setListener(listenerItem)
            }
            WIDGET_INPUT_RAPPI -> TextInputRappiWidget(context).apply {
                bind(widget.first as TextInputModelWidget)
                this.setTextChangedListener(listenerItem)
            }
            WIDGET_SPINNER -> SpinnerOutlineWidget(context).apply {
                bind(widget.first as SpinnerModelWidget)
                setItemSelectedListener(listenerItem)
            }
            else -> null
        }
}
