package com.jonathanbernal.libbase.widgets.view

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.jonathanbernal.libbase.list.factories.WidgetFactory
import com.jonathanbernal.libbase.list.items.GenericItemView
import com.jonathanbernal.libbase.widgets.model.views.LinearLayoutModelWidget
import com.jonathanbernal.libbase.widgets.model.views.WEIGHT_RES
import com.jonathanbernal.libbase.widgets.model.views.WidgetOrientation
import com.jonathanbernal.libbase.widgets.model.views.getGravityView

class LinearLayoutViewWidget(context: Context, val listener: (Any) -> Unit = {}) :
    LinearLayout(context),
    GenericItemView<LinearLayoutModelWidget> {

    private lateinit var item: LinearLayoutModelWidget

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun bind(item: LinearLayoutModelWidget) {
        this.item = item
        initItem()
    }

    private fun initItem() {
        orientation = WidgetOrientation.valueOf(item.orientation).value
        item.backgroundColor?.let { setBackgroundResource(it) }
        addMargin()
        addPadding()
        addViews(item.widgets)
        isVisible = item.visibility
        item.gravity?.getGravityView()?.let {
            gravity = it
        }
        item.weightSum?.toFloat()?.let {
            weightSum = it
        }
        item.extras?.get(WEIGHT_RES)?.let {
            addWeight(it.toString().toFloat())
        }
    }

    private fun addPadding() {
        val top: Int = resources.getDimensionPixelSize(item.padding.spacingTop)
        val bottom: Int = resources.getDimensionPixelSize(item.padding.spacingBottom)
        val left: Int = resources.getDimensionPixelSize(item.padding.spacingLeft)
        val right: Int = resources.getDimensionPixelSize(item.padding.spacingRight)
        setPadding(left, top, right, bottom)
    }

    private fun addWeight(weight: Float) {
        layoutParams = LayoutParams(
            item.widthRes ?: LayoutParams.WRAP_CONTENT,
            item.heightRes ?: LayoutParams.WRAP_CONTENT,
            weight
        )
    }

    private fun addMargin() {
        val marginLayoutParams = layoutParams as MarginLayoutParams
        marginLayoutParams.topMargin = resources.getDimensionPixelSize(item.margin.spacingTop)
        marginLayoutParams.bottomMargin = resources.getDimensionPixelSize(item.margin.spacingBottom)
        marginLayoutParams.leftMargin = resources.getDimensionPixelSize(item.margin.spacingLeft)
        marginLayoutParams.rightMargin = resources.getDimensionPixelSize(item.margin.spacingRight)
        layoutParams = marginLayoutParams
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

    override fun getData(): LinearLayoutModelWidget {
        return item
    }

    override fun getView(): View = rootView
}
