package com.rappi.components.widgets.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import com.jonathanbernal.libbase.list.items.GenericItemView
import com.jonathanbernal.libbase.widgets.model.views.ButtonModelWidget
import com.jonathanbernal.libbase.widgets.model.views.WEIGHT_RES
import com.jonathanbernal.libbase.widgets.model.views.getGravityView

class ButtonViewWidget(context: Context) :
    AppCompatButton(context),
    GenericItemView<ButtonModelWidget> {

    private lateinit var item: ButtonModelWidget

    private val topPadding by lazy { resources.getDimensionPixelSize(item.padding.spacingTop) }

    private val bottomPadding by lazy { resources.getDimensionPixelSize(item.padding.spacingBottom) }

    private val leftPadding by lazy { resources.getDimensionPixelSize(item.padding.spacingLeft) }

    private val rightPadding by lazy { resources.getDimensionPixelSize(item.padding.spacingRight) }

    private var listener: (Any) -> Unit = {}

    init {
        layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun bind(item: ButtonModelWidget) {
        this.item = item
        initItem()
        setOnClickListener {
            item.action?.let {
                listener(it)
            }
        }
    }

    private fun addWeight(weight: Float) {
        layoutParams = LinearLayout.LayoutParams(
            item.widthRes ?: LinearLayout.LayoutParams.MATCH_PARENT,
            item.widthRes ?: LinearLayout.LayoutParams.WRAP_CONTENT,
            weight
        )
    }

    private fun initItem() {
        setBackgroundResource(item.backgroundColor)
        setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
        addMargin()
        addPadding()
        text = item.text
        item.gravity?.getGravityView()?.let {
            gravity = it
        }
        item.extras?.get(WEIGHT_RES)?.let {
            val weight = (it as Double).toFloat()
            addWeight(weight)
        }
    }

    private fun addPadding() {
        val top: Int = resources.getDimensionPixelSize(item.padding.spacingTop)
        val bottom: Int = resources.getDimensionPixelSize(item.padding.spacingBottom)
        val left: Int = resources.getDimensionPixelSize(item.padding.spacingLeft)
        val right: Int = resources.getDimensionPixelSize(item.padding.spacingRight)
        setPadding(left, top, right, bottom)
    }

    private fun addMargin() {
        val marginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
        marginLayoutParams.topMargin = resources.getDimensionPixelSize(item.margin.spacingTop)
        marginLayoutParams.bottomMargin = resources.getDimensionPixelSize(item.margin.spacingBottom)
        marginLayoutParams.leftMargin = resources.getDimensionPixelSize(item.margin.spacingLeft)
        marginLayoutParams.rightMargin = resources.getDimensionPixelSize(item.margin.spacingRight)
        layoutParams = marginLayoutParams
    }

    override fun getData(): ButtonModelWidget {
        return item
    }

    fun setListener(listener: (Any) -> Unit = {}) {
        this.listener = listener
    }

    override fun getView(): View = rootView
}
