package com.jonathanbernal.libbase.widgets.view

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.list.items.GenericItemView
import com.jonathanbernal.libbase.widgets.model.RadioButtonModelWidget

class RadioButtonViewWidget(context: Context) :
    AppCompatRadioButton(ContextThemeWrapper(context, R.style.RadioButton_Green), null, 0),
    GenericItemView<RadioButtonModelWidget> {

    private lateinit var model: RadioButtonModelWidget

    private var listener: (Any) -> Unit = {}

    override fun bind(item: RadioButtonModelWidget) {
        model = item
        initItem(item)
        setTextByModel(item)
        addPadding()
        addMargin()
    }

    private fun setTextByModel(item: RadioButtonModelWidget) {
        val itemText = if (item.resourceId != 0) context.getText(item.resourceId) else item.text
        text = itemText
    }

    private fun initItem(item: RadioButtonModelWidget) {
        setFontStyle(item)
        initCheck(item)
        addLayoutDirection(item)
        setBackgroundResource(item.backgroundColor)
        item.buttonTint?.let {
            buttonTintList = ContextCompat.getColorStateList(context, it)
        }
        addListener()
    }

    private fun initCheck(item: RadioButtonModelWidget) {
        isChecked = item.isChecked
        isEnabled = item.enable
    }

    private fun setFontStyle(item: RadioButtonModelWidget) {
        setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimensionPixelSize(model.fontSize).toFloat()
        )
        if (item.textStyle != -1) TextViewCompat.setTextAppearance(this, item.textStyle)
    }

    private fun addListener() {
        setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) listener(model)
        }
    }

    private fun addLayoutDirection(item: RadioButtonModelWidget) {
        layoutDirection = item.layoutDirection
        textAlignment = item.textAlignment
        layoutParams = RadioGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun addPadding() {
        val top: Int = resources.getDimensionPixelSize(model.padding.spacingTop)
        val bottom: Int = resources.getDimensionPixelSize(model.padding.spacingBottom)
        val left: Int = resources.getDimensionPixelSize(model.padding.spacingLeft)
        val right: Int = resources.getDimensionPixelSize(model.padding.spacingRight)
        compoundDrawablePadding = resources.getDimensionPixelSize(R.dimen.spacing_xxxhuge)
        setPadding(left, top, right, bottom)
    }

    private fun addMargin() {
        val marginLayoutParams = layoutParams as RadioGroup.LayoutParams
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

    override fun getData(): RadioButtonModelWidget = model

    override fun getView(): View = rootView
}
