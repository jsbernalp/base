package com.jonathanbernal.libbase.widgets.view

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.MutableLiveData
import com.jonathanbernal.libbase.list.items.GenericItemView
import com.jonathanbernal.libbase.widgets.model.views.BaseModelWidget
import com.jonathanbernal.libbase.widgets.model.views.LINE_EXTRA_ADD_DIMEN
import com.jonathanbernal.libbase.widgets.model.views.WEIGHT_RES
import com.jonathanbernal.libbase.widgets.model.views.getGravityView
import com.jonathanbernal.libbase.extensions.resize
import com.jonathanbernal.libbase.extensions.rotate
import com.jonathanbernal.libbase.extensions.tint
import com.jonathanbernal.libbase.widgets.fromHtml
import com.jonathanbernal.libbase.widgets.model.DrawableTextModel
import com.jonathanbernal.libbase.widgets.model.TextModelWidget
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class TextViewWidget(context: Context) :
    AppCompatTextView(context),
    GenericItemView<TextModelWidget>,
    LifecycleOwner,
    CoroutineScope {

    private lateinit var model: TextModelWidget

    private val liveData = MutableLiveData<BaseModelWidget>()

    private val job = SupervisorJob()

    private val lifecycleOwner = LifecycleRegistry(this)

    private var listener: (Any) -> Unit = {}

    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    init {
        liveData.observe(this, { onDataChange(it) })
        addListener()
    }

    override fun bind(item: TextModelWidget) {
        job.cancelChildren()
        cleanStyleText()
        model = item
        addLayoutParams(item)
        liveData.postValue(item)
    }

    private fun onDataChange(data: BaseModelWidget) {
        if (data is TextModelWidget) {
            initItem(data)
            applyDrawable(data.drawable)
            compoundDrawablePadding = resources.getDimensionPixelSize(model.padding.withDrawable)
            gravity = data.gravity?.getGravityView() ?: Gravity.CENTER_VERTICAL
            data.extras?.get(LINE_EXTRA_ADD_DIMEN)?.let {
                setLineSpacing(context.resources.getDimensionPixelSize(it as Int).toFloat(), 1f)
            }
        }
    }

    private fun cleanStyleText() {
        if (text is SpannableString) {
            val content: SpannableString = text as SpannableString
            val spans = content.getSpans(0, text.length, StyleSpan::class.java)
            for (styleSpan in spans) content.removeSpan(styleSpan)
        }
    }

    private fun addLayoutParams(item: TextModelWidget) {
        item.extras?.get(WEIGHT_RES)?.let {
            layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    it.toString().toFloat()
                )
        } ?: run {
            layoutParams =
                ViewGroup.MarginLayoutParams(item.width, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    private fun setTextByModel(item: TextModelWidget) {
        val itemText = if (item.resourceId != 0) context.getText(item.resourceId) else item.text
        text = if (item.isHtmlText) fromHtml(itemText.toString()) else itemText
    }

    private fun initItem(item: TextModelWidget) {
        setTextByModel(item)
        setFontStyle(item)
        addPadding()
        addMargin()
        updateBackground(item)
    }

    private fun setFontStyle(item: TextModelWidget) {
        setTextSize(
            TypedValue.COMPLEX_UNIT_PX,
            resources.getDimensionPixelSize(model.fontSize).toFloat()
        )
        setTextColor(ContextCompat.getColor(context, item.textColor))
        setTypeface(this.typeface, item.textStyle)
        ellipsize = item.ellipsize
        if (item.maxLines != null) {
            maxLines = item.maxLines!!
        }
        if (item.style != -1) TextViewCompat.setTextAppearance(this, item.style)
    }

    private fun updateBackground(item: TextModelWidget) {
        if (addBackgroundFromStyle(item).not() && item.backgroundColor == -1) setBackgroundResource(R.color.white)
        else if (item.backgroundColor != -1) setBackgroundResource(item.backgroundColor)
    }

    private fun addBackgroundFromStyle(item: TextModelWidget): Boolean {
        var attributeResourceId = 0
        if (item.style != 1) {
            val typed: TypedArray = context.theme.obtainStyledAttributes(item.style, intArrayOf(R.attr.background))
            attributeResourceId = typed.getResourceId(0, 0)
            setBackgroundResource(attributeResourceId)
        }
        return attributeResourceId != 0
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

    fun applyDrawable(drawableTextModel: DrawableTextModel) {
        val widthRes = drawableTextModel.widthRes ?: drawableTextModel.squareSizeRes
        val heightRes = drawableTextModel.heightRes ?: drawableTextModel.squareSizeRes

        val drawableLeft: Drawable? = drawableTextModel.drawableLeft?.let {
            AppCompatResources.getDrawable(context, it)
                ?.tint(context, drawableTextModel.tintColorRes)
                ?.resize(context, widthRes, heightRes)
        }
        val drawableRight: Drawable? = drawableTextModel.drawableRight?.let {
            AppCompatResources.getDrawable(context, it)
                ?.tint(context, drawableTextModel.tintColorRes)
                ?.resize(context, widthRes, heightRes)
                ?.rotate(context, drawableTextModel.rotationDegree)
        }
        val drawableBottom: Drawable? = drawableTextModel.drawableBottom?.let {
            AppCompatResources.getDrawable(context, it)
                ?.tint(context, drawableTextModel.tintColorRes)
                ?.resize(context, widthRes, heightRes)
        }
        val drawableTop: Drawable? = drawableTextModel.drawableTop?.let {
            AppCompatResources.getDrawable(context, it)
                ?.tint(context, drawableTextModel.tintColorRes)
                ?.resize(context, widthRes, heightRes)
        }

        setCompoundDrawablesWithIntrinsicBounds(
            drawableLeft,
            drawableTop,
            drawableRight,
            drawableBottom
        )

        drawableTextModel.compoundDrawablePaddingRes?.let {
            compoundDrawablePadding = context.resources.getDimensionPixelSize(it)
        }
    }

    private fun addListener() {
        setOnClickListener {
            model.action?.let { listener(it) }
        }
    }

    fun setListener(listener: (Any) -> Unit = {}) {
        this.listener = listener
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_START)
        model.functionScopeContext?.let {
            launch(Dispatchers.Main) {
                model.functionScopeContext?.invoke(context, liveData)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        job.cancelChildren()
    }

    override fun getData(): TextModelWidget = model

    override fun getView(): View = rootView

    override fun getLifecycle() = lifecycleOwner
}
