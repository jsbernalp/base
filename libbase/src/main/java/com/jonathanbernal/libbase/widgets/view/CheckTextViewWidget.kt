package com.jonathanbernal.libbase.widgets.view

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import com.jonathanbernal.libbase.list.items.GenericItemView
import com.jonathanbernal.libbase.widgets.model.views.WEIGHT_RES
import com.jonathanbernal.libbase.widgets.model.views.getGravityView
import com.jonathanbernal.libbase.widgets.model.views.CheckBoxModelWidget

class CheckTextViewWidget(context: Context) :
    LinearLayout(context),
    GenericItemView<CheckBoxModelWidget> {

    private val checkBox = AppCompatCheckBox(context)

    private lateinit var model: CheckBoxModelWidget

    private var listener: (Any) -> Unit = {}

    init {
        addView(checkBox)
    }

    override fun bind(item: CheckBoxModelWidget) {
        model = item
        addLayoutParams(item)
        initItem(item)
        setTextByModel(item)
        gravity = item.gravity?.getGravityView() ?: Gravity.CENTER_VERTICAL
    }

    private fun addLayoutParams(item: CheckBoxModelWidget) {
        item.extras?.get(WEIGHT_RES)?.let {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        } ?: run {
            layoutParams =
                ViewGroup.MarginLayoutParams(item.width, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    private fun setTextByModel(item: CheckBoxModelWidget) {
        val itemText = if (item.resourceId != 0) context.getText(item.resourceId) else item.text
        checkBox.text = itemText
    }

    private fun initItem(item: CheckBoxModelWidget) {
        setFontStyle(item)
        initCheck(item)
        addPadding()
        addMargin()
        setBackgroundResource(item.backgroundColor)

        item.buttonTint?.let {
            checkBox.buttonTintList = ContextCompat.getColorStateList(context, it)
        }

        addListener()
    }

    private fun initCheck(item: CheckBoxModelWidget) {
        checkBox.isChecked = item.isChecked
        checkBox.isEnabled = item.enable
    }

    private fun setFontStyle(item: CheckBoxModelWidget) {
        checkBox.setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimensionPixelSize(model.fontSize).toFloat()
        )
        checkBox.setTextColor(ContextCompat.getColor(context, item.textColor))
        checkBox.setTypeface(checkBox.typeface, item.textStyle)
    }

    private fun addPadding() {
        val top: Int = resources.getDimensionPixelSize(model.padding.spacingTop)
        val bottom: Int = resources.getDimensionPixelSize(model.padding.spacingBottom)
        val left: Int = resources.getDimensionPixelSize(model.padding.spacingLeft)
        val right: Int = resources.getDimensionPixelSize(model.padding.spacingRight)
        setPadding(left, top, right, bottom)
    }

    private fun addMargin() {
        val marginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
        marginLayoutParams.topMargin =
            resources.getDimensionPixelSize(model.margin.spacingTop)
        marginLayoutParams.bottomMargin =
            resources.getDimensionPixelSize(model.margin.spacingBottom)
        marginLayoutParams.leftMargin =
            resources.getDimensionPixelSize(model.margin.spacingLeft)
        marginLayoutParams.rightMargin =
            resources.getDimensionPixelSize(model.margin.spacingRight)
        layoutParams = marginLayoutParams
    }

    private fun addListener() {
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) model.onCheckedAction?.let { listener(it) }
            else model.unCheckedAction?.let { listener(it) }
        }
    }

    fun setListener(listener: (Any) -> Unit = {}) {
        this.listener = listener
    }

    override fun getData(): CheckBoxModelWidget = model

    override fun getView(): View = rootView
}
