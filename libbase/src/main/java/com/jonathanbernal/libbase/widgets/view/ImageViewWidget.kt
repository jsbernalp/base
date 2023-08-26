package com.jonathanbernal.libbase.widgets.view

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.jonathanbernal.libbase.list.items.GenericItemView
import com.jonathanbernal.libbase.widgets.model.views.ImageModelWidget
import com.jonathanbernal.libbase.widgets.model.views.MATCH_PARENT_VALUE
import com.jonathanbernal.libbase.widgets.model.views.WEIGHT_RES
import com.jonathanbernal.libbase.widgets.model.views.WRAP_PARENT_VALUE
import com.jonathanbernal.libbase.extensions.getDrawableCompat
import com.jonathanbernal.libbase.extensions.tintWithHexColor

class ImageViewWidget(context: Context) :
    AppCompatImageView(context),
    GenericItemView<ImageModelWidget> {

    private lateinit var item: ImageModelWidget

    private val topPadding by lazy { resources.getDimensionPixelSize(item.padding.spacingTop) }

    private val bottomPadding by lazy { resources.getDimensionPixelSize(item.padding.spacingBottom) }

    private val leftPadding by lazy { resources.getDimensionPixelSize(item.padding.spacingLeft) }

    private val rightPadding by lazy { resources.getDimensionPixelSize(item.padding.spacingRight) }

    private var listener: (Any) -> Unit = {}

    init {
        layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.WRAP_CONTENT,
            ViewGroup.MarginLayoutParams.WRAP_CONTENT
        )
    }

    override fun bind(item: ImageModelWidget) {
        this.item = item
        initItem()
        addOnClick()
    }

    private fun addOnClick() {
        setOnClickListener {
            item.action?.let { model ->
                listener(model)
            }
        }
    }

    private fun addWeight(weight: Float) {
        layoutParams = LinearLayout.LayoutParams(
            handleSizeRes(item.width ?: LinearLayout.LayoutParams.WRAP_CONTENT),
            handleSizeRes(item.height ?: LinearLayout.LayoutParams.WRAP_CONTENT),
            weight
        )
    }

    private fun initItem() {
        item.width?.let {
            layoutParams.width = handleSizeRes(it)
        }
        item.height?.let {
            layoutParams.height = handleSizeRes(it)
        }
        setPadding(leftPadding, topPadding, rightPadding, bottomPadding)
        addMargin()
        loadImage()

        item.backgroundRes?.let {
            background = context.getDrawableCompat(it)?.tintWithHexColor(item.backgroundColor)
        }
        item.extras?.get(WEIGHT_RES)?.let {
            addWeight(it.toString().toFloat())
        }
    }

    private fun addMargin() {
        val marginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams
        marginLayoutParams.topMargin = resources.getDimensionPixelSize(item.margin.spacingTop)
        marginLayoutParams.bottomMargin = resources.getDimensionPixelSize(item.margin.spacingBottom)
        marginLayoutParams.leftMargin = resources.getDimensionPixelSize(item.margin.spacingLeft)
        marginLayoutParams.rightMargin = resources.getDimensionPixelSize(item.margin.spacingRight)
        layoutParams = marginLayoutParams
    }

    private fun loadImage() {
        item.image?.let {
            Glide.with(this)
                .load(it)
                .into(this)
        } ?: item.imageRes?.let {
            setImageDrawable(context.getDrawableCompat(it))
            item.imageTint?.let {
                ImageViewCompat.setImageTintList(
                    this,
                    ColorStateList.valueOf(
                        ContextCompat.getColor(context, it)
                    )
                )
            }
        } ?: if (item.imageUrl.isNotBlank() && URLUtil.isValidUrl(item.imageUrl)) {
            Glide.with(this)
                .load(item.imageUrl)
                .centerCrop()
                .apply(getRoundCornersOptions(item.cornerRadius))
                .into(this)
        } else {

        }
    }

    private fun getRoundCornersOptions(cornerRadius: Int?) = RequestOptions().apply {
        cornerRadius?.takeIf { it != 0 }?.let {
            transform(
                CenterCrop(),
                RoundedCorners(
                    this@ImageViewWidget.resources.getDimensionPixelSize(cornerRadius)
                )
            )
        }
    }

    private fun handleSizeRes(sizeRes: Int) = when (sizeRes) {
        MATCH_PARENT_VALUE -> ViewGroup.MarginLayoutParams.MATCH_PARENT
        WRAP_PARENT_VALUE -> ViewGroup.MarginLayoutParams.WRAP_CONTENT
        else -> resources.getDimensionPixelSize(sizeRes)
    }

    fun setListener(viewListener: (Any) -> Unit) {
        listener = viewListener
    }

    override fun getData(): ImageModelWidget {
        return item
    }

    override fun getView(): View = rootView
}
