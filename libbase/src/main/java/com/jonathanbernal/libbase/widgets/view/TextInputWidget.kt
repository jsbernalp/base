package com.jonathanbernal.libbase.widgets.view

import android.content.Context
import android.content.res.ColorStateList
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jonathanbernal.libbase.R
import com.jonathanbernal.libbase.extensions.addFilterMaxLength
import com.jonathanbernal.libbase.extensions.addLettersAndDigitsOnlyFilter
import com.jonathanbernal.libbase.extensions.addLettersOnlyFilter
import com.jonathanbernal.libbase.extensions.applyDrawable
import com.jonathanbernal.libbase.list.items.GenericItemView
import com.jonathanbernal.libbase.widgets.actions.WidgetActionInputTextChange
import com.jonathanbernal.libbase.widgets.model.views.InputTypeOption
import com.jonathanbernal.libbase.widgets.model.views.TextInputModelWidget
import com.jonathanbernal.libbase.widgets.model.views.WEIGHT_RES

class TextInputWidget(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox,
) : TextInputLayout(ContextThemeWrapper(context, defStyleAttr), attrs),
    GenericItemView<TextInputModelWidget> {

    private lateinit var item: TextInputModelWidget

    private var editText: TextInputEditText? = null

    private var textChangedListener: ((Any) -> Unit) = { }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        boxBackgroundMode = BOX_BACKGROUND_OUTLINE
        boxBackgroundColor = ContextCompat.getColor(context, android.R.color.transparent)
    }

    override fun bind(item: TextInputModelWidget) {
        this.item = item
        initView()
    }

    private fun initView() {
        addInputEditText()
        hint = item.hint
        addExtras()
        setTypeEditText(item.type)
        setMaxLength(item.maxLength)
        setTextValue(item.text)
        setTagView()
        addMargin()
        addPadding()
        setBoxStyle()
        setFocusable()
        editText?.applyDrawable(item.drawable)
        setListener()
    }

    private fun setTagView() {
        item.tag?.let {
            tag = it
        }
    }

    private fun addExtras() {
        item.extras?.get(WEIGHT_RES)?.let {
            addWeight(it.toString().toFloat())
        }
    }

    private fun setListener() {
        editText?.setOnClickListener {
            item.action?.let { action ->
                textChangedListener(action)
            }
        }
    }

    fun setBoxStyle() {
        item.backgroundColor?.let {
            boxBackgroundColor = ContextCompat.getColor(context, it)
        }

        item.textColorHint?.let {
            hintTextColor = getColorStateList(context, it)
        }

        item.boxCornerRadius?.let {
            setBoxCornerRadiiResources(
                it.radiusTopStart,
                it.radiusTopEnd,
                it.radiusBottomEnd,
                it.radiusBottomStart,
            )
        }
    }

    fun setFocusable() {
        editText?.isFocusable = item.focusable
    }

    fun setDrawableRight(@DrawableRes icon: Int) {
        context.getDrawable(icon)?.let { drawable ->
            editText?.setCompoundDrawablesWithIntrinsicBounds(
                null,
                null,
                drawable,
                null
            )
        }
    }

    fun setTypeEditText(type: com.jonathanbernal.libbase.widgets.model.views.InputTypeOption?) {
        when (type) {
            InputTypeOption.TELEPHONE -> editText?.inputType = InputType.TYPE_CLASS_NUMBER
            InputTypeOption.EMAIL ->
                editText?.inputType =
                    InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            InputTypeOption.TEXT -> {
                editText?.inputType = InputType.TYPE_CLASS_TEXT
                editText?.addLettersOnlyFilter()
            }
            InputTypeOption.PASSWORD ->
                editText?.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            InputTypeOption.ALPHANUMERIC -> editText?.addLettersAndDigitsOnlyFilter()
            else -> editText?.inputType = InputType.TYPE_CLASS_TEXT
        }
    }

    private fun setMaxLength(maxLength: Int?) {
        maxLength?.let {
            editText?.addFilterMaxLength(it)
        }
    }

    private fun getColorListBox(strokeColor: Int): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_focused),
            intArrayOf(android.R.attr.state_hovered),
            intArrayOf(android.R.attr.state_enabled),
            intArrayOf(),
        )

        val colors = intArrayOf(
            context.getColor(R.color.black),
            context.getColor(strokeColor),
            context.getColor(strokeColor),
            context.getColor(strokeColor),
        )
        return ColorStateList(states, colors)
    }

    fun setTextValue(textValue: String?) {
        textValue?.let {
            editText?.setText(it)
        }
    }

    private fun addInputEditText() {
        if (editText == null) {
            editText = TextInputEditText(this@TextInputWidget.context).apply {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            }
            initListenerTextChange()
            editText?.setSingleLine()
            addView(editText)
        }
    }

    private fun initListenerTextChange() {
        editText?.doOnTextChanged { text, _, _, _ ->
            textChangedListener(
                WidgetActionInputTextChange(
                    item.id,
                    text.toString()
                )
            )
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
            item.width,
            item.height,
            weight
        )
    }

    private fun addMargin() {
        val marginLayoutParams = layoutParams as MarginLayoutParams
        marginLayoutParams.apply {
            topMargin = resources.getDimensionPixelSize(item.margin.spacingTop)
            bottomMargin = resources.getDimensionPixelSize(item.margin.spacingBottom)
            leftMargin = resources.getDimensionPixelSize(item.margin.spacingLeft)
            rightMargin = resources.getDimensionPixelSize(item.margin.spacingRight)
        }
        layoutParams = marginLayoutParams
    }

    fun setTextChangedListener(listener: (Any) -> Unit = {}) {
        this.textChangedListener = listener
    }

    override fun getData(): TextInputModelWidget = item

    override fun getView(): View = rootView
}
