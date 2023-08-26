package com.jonathanbernal.libbase.widgets.view

import android.content.Context
import android.view.View
import android.widget.RadioGroup
import com.jonathanbernal.libbase.list.factories.WidgetFactory
import com.jonathanbernal.libbase.list.items.GenericItemView
import com.jonathanbernal.libbase.widgets.model.views.RadioButtonGroupModelWidget

class RadioButtonGroupViewWidget(context: Context) :
    RadioGroup(context),
    GenericItemView<RadioButtonGroupModelWidget> {

    private lateinit var model: RadioButtonGroupModelWidget

    private var listener: (Any) -> Unit = {}

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun bind(item: RadioButtonGroupModelWidget) {
        this.model = item
        initItem()
    }

    private fun initItem() {
        addMargin()
        addPadding()
        setBackgroundResource(model.backgroundColor)
        addViews(model.widgets)
    }

    private fun addViews(widgets: List<Pair<Any, Int>?>) {
        removeAllViews()
        widgets.forEach {
            it?.let { pair ->
                WidgetFactory().onCreateViewForLinearWidget(context, pair, listener)?.let { item ->
                    addView(item.getView())
                }
            }
        }
    }

    private fun addPadding() {
        val top: Int = resources.getDimensionPixelSize(model.padding.spacingTop)
        val bottom: Int = resources.getDimensionPixelSize(model.padding.spacingBottom)
        val left: Int = resources.getDimensionPixelSize(model.padding.spacingLeft)
        val right: Int = resources.getDimensionPixelSize(model.padding.spacingRight)
        setPadding(left, top, right, bottom)
    }

    private fun addMargin() {
        val marginLayoutParams = layoutParams as MarginLayoutParams
        marginLayoutParams.topMargin = resources.getDimensionPixelSize(model.margin.spacingTop)
        marginLayoutParams.bottomMargin =
            resources.getDimensionPixelSize(model.margin.spacingBottom)
        marginLayoutParams.leftMargin = resources.getDimensionPixelSize(model.margin.spacingLeft)
        marginLayoutParams.rightMargin = resources.getDimensionPixelSize(model.margin.spacingRight)
        layoutParams = marginLayoutParams
    }

    fun setListener(listener: (Any) -> Unit = {}) {
        this.listener = listener
    }

    override fun getData(): RadioButtonGroupModelWidget = model

    override fun getView(): View? = rootView
}
